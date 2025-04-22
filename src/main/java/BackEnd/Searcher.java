package BackEnd;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Searcher {
    private String path;
    private IndexReader reader;
    private IndexSearcher indexSearcher;
    private Query query;
    ScoreDoc[] hits;

    public Searcher() throws IOException {
        DirectoryMaker directoryMaker=new DirectoryMaker();
        this.path=directoryMaker.getPath();
        createSearcher();
    }

    public void makeScore() throws IOException {
        this.hits= indexSearcher.search(this.query,11000).scoreDocs;
    }

    public void createSearcher() throws IOException {
        Directory directory = FSDirectory.open(Paths.get(this.path));
        this.reader= DirectoryReader.open(directory);
        this.indexSearcher=new IndexSearcher(this.reader);
    }

    public void makeTermQuery(String field, String term,Analyzer analyzer) throws ParseException, IOException {
        this.query = new QueryParser(field, analyzer).parse(term);
        makeScore();
    }

    public void makeWildCardQuery(String field,String wildcardTerm, Analyzer analyzer) throws ParseException, IOException {
        QueryParser parser = new QueryParser(field, analyzer);
        parser.setAllowLeadingWildcard(true);
        this.query = parser.parse(wildcardTerm);
        makeScore();
    }

    public  void makePrefixQuery(String field, String prefixTerm, Analyzer analyzer) throws ParseException, IOException {
        QueryParser parser = new QueryParser(field, analyzer);
        this.query = parser.parse(prefixTerm+"*");
        makeScore();
    }

    public  void makeFuzzyQuery(String field, String fuzzyTerm, Analyzer analyzer) throws ParseException, IOException {
        QueryParser parser = new QueryParser(field, analyzer);
        parser.setFuzzyMinSim(0.3f);
        this.query = parser.parse(fuzzyTerm + "~");
        makeScore();
    }

    public void makeBooleanQuery(String field1, String field2,String term1,String term2,Analyzer filed1Analyzer, Analyzer filed2Analyzer,Integer relOp) throws ParseException, IOException {
        BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();

        QueryParser parser1 = new QueryParser(field1,filed1Analyzer);
        parser1.setAllowLeadingWildcard(true);

        QueryParser parser2 = new QueryParser(field2,filed2Analyzer);
        parser2.setAllowLeadingWildcard(true);

        Query termQuery = parser1.parse(term1);
        Query termQuery1 = parser2.parse(term2);

        if(relOp==0){
            booleanQueryBuilder.add(termQuery, BooleanClause.Occur.SHOULD);
            booleanQueryBuilder.add(termQuery1, BooleanClause.Occur.MUST_NOT);
        }

        if(relOp==1){
            booleanQueryBuilder.add(termQuery, BooleanClause.Occur.MUST);
            booleanQueryBuilder.add(termQuery1, BooleanClause.Occur.MUST);
        }

        if(relOp==2){
            booleanQueryBuilder.add(termQuery, BooleanClause.Occur.SHOULD);
            booleanQueryBuilder.add(termQuery1, BooleanClause.Occur.SHOULD);
        }

        this.query = booleanQueryBuilder.build();
        makeScore();
    }

    public void makePhraseQuery(String field, String term1, String term2,Analyzer analyzer) throws ParseException, IOException {
        PhraseQuery.Builder phraseQueryBuilder = new PhraseQuery.Builder();
        phraseQueryBuilder.add(new Term(field,term1));
        phraseQueryBuilder.add(new Term(field,term2));
        phraseQueryBuilder.setSlop(2);
        PhraseQuery phraseQuery = phraseQueryBuilder.build();

        QueryParser parser = new QueryParser(field,analyzer);
        this.query= parser.parse(phraseQuery.toString());
        makeScore();
    }

    public ArrayList<String> getHits(String field) throws IOException, InvalidTokenOffsetsException {
        ArrayList<String> tempResults=new ArrayList<String>();

        for(int index = 0; index < this.hits.length; index++) tempResults.add(reader.document(this.hits[index].doc).get(field)+"\n");

        return  tempResults;
    }

    public ArrayList<String> getHitsColored(String field) throws IOException, InvalidTokenOffsetsException {
        ArrayList<String> tempResults = new ArrayList<String>();

        QueryScorer scorer = new QueryScorer(this.query, field);
        SimpleHTMLFormatter htmlFormatter = new SimpleHTMLFormatter("<b><font color=\"red\">", "</font></b>");
        Highlighter highlighter = new Highlighter(htmlFormatter, scorer);

        for (int index = 0; index < this.hits.length; index++) {
            String text = reader.document(this.hits[index].doc).get(field);
            TokenStream tokenStream = TokenSources.getTokenStream(reader, this.hits[index].doc, field,CustomAnalyzer.builder().withTokenizer("standard").addTokenFilter("lowercase").build());
            TextFragment[] frag = highlighter.getBestTextFragments(tokenStream, text, false, 10);
            StringBuilder stringBuilder = new StringBuilder();
            for (TextFragment textFragment : frag) {
                if ((textFragment != null) && (textFragment.getScore() > 0)) {
                    stringBuilder.append(textFragment.toString());
                }
            }
            tempResults.add(stringBuilder.toString() + "\n");
        }

        return tempResults;
    }
}
