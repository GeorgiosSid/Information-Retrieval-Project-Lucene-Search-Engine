package BackEnd;

import com.opencsv.exceptions.CsvValidationException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.synonym.SynonymFilterFactory;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
public class BackEndEngine {
    IndexMaker indexMaker;
    Searcher searcher;
    History history;
    String givenSearch;
    Integer relOp;
    ArrayList<String> inputQuery;
    Word2Vec model;

    public  BackEndEngine() throws IOException, CsvValidationException {
        this.indexMaker=new IndexMaker();
        this.indexMaker.writeDirectory();
        this.searcher=new Searcher();
        this.history=new History();
        this.model = WordVectorSerializer.readWord2VecModel(new File("C:/Programming/Lucene/Project_4674_4789/GoogleNews-vectors-negative300.bin"), false);
    }

    private Analyzer analyzerDecision(String field) throws IOException {
        if(field.equalsIgnoreCase("text")|| field.equalsIgnoreCase("link")) return CustomAnalyzer.builder().withTokenizer("standard")
                                                                                                                        .addTokenFilter(SynonymFilterFactory.class, "synonyms", "synonyms_en.txt", "ignoreCase", "true")
                                                                                                                        .addTokenFilter("lowercase").addTokenFilter("stop").addTokenFilter("porterstem").build();

        else return CustomAnalyzer.builder().withTokenizer("standard").addTokenFilter("lowercase").build();
    }

    public void executeQuery(Integer queryNumber,boolean isSelected) throws ParseException, IOException {
        if(!isSelected)this.history.addHistory(this.givenSearch);

        if(queryNumber==1) this.searcher.makeTermQuery(inputQuery.get(0),inputQuery.get(1),analyzerDecision(inputQuery.get(0)));

        if(queryNumber==2) this.searcher.makeWildCardQuery(inputQuery.get(0),inputQuery.get(1),analyzerDecision(inputQuery.get(0)));

        if(queryNumber==3) this.searcher.makePrefixQuery(inputQuery.get(0),inputQuery.get(1),analyzerDecision(inputQuery.get(0)));

        if(queryNumber==4) this.searcher.makeFuzzyQuery(inputQuery.get(0),inputQuery.get(1),analyzerDecision(inputQuery.get(0)));

        if(queryNumber==5) this.searcher.makeBooleanQuery(inputQuery.get(0),inputQuery.get(1),inputQuery.get(2),inputQuery.get(3),analyzerDecision(inputQuery.get(0)),analyzerDecision(inputQuery.get(1)),this.relOp);

        if(queryNumber==6) this.searcher.makePhraseQuery(inputQuery.get(0),inputQuery.get(1),inputQuery.get(2),analyzerDecision(inputQuery.get(0)));
    }

    public ArrayList<String> getRecommendQuery(){return  this.history.getRecommendQuery();}

    public ArrayList<String> getResults(String field) throws IOException, InvalidTokenOffsetsException {return this.searcher.getHits(field);}

    public ArrayList<String> getResultsColored(String field) throws IOException, InvalidTokenOffsetsException {return this.searcher.getHitsColored(field);}

    public Collection<String> getSimilar(String targetWord){return this.model.wordsNearest(targetWord, 20);}

    public void setInputQuery(ArrayList<String> inputQuery) {this.inputQuery = inputQuery;}

    public void setGivenSearch(String givenSearch) {this.givenSearch = givenSearch;}

    public void setRelOp(Integer relOp) {
        this.relOp = relOp;
    }

    public void clearHistory() throws IOException {this.history.clearHistory();}

}
