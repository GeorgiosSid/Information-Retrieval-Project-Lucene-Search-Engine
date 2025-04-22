package BackEnd;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.synonym.SynonymFilterFactory;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.FSDirectory;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class IndexMaker{
    DirectoryMaker directoryMaker;
    CSVReader reader;
    IndexWriter indexWriter;

    public  IndexMaker() throws IOException {
        super();
        this.directoryMaker=new DirectoryMaker();
        this.directoryMaker.createDirectory();
        this.reader = new CSVReader(new FileReader("C://Programming//Lucene//Project_4674_4789//spotify_millsongdata.csv"));
    }

    private boolean checkDirectory() throws IOException {
        File directory = new File(this.directoryMaker.getPath());

        return directory.isDirectory() && directory.listFiles().length == 0;
    }

    private void createIndexWriter() throws IOException {
        FSDirectory fsDirectory=FSDirectory.open(Paths.get(this.directoryMaker.getPath()));

        Analyzer standardAnalyzer = CustomAnalyzer.builder().withTokenizer("standard")
                .addTokenFilter(SynonymFilterFactory.class, "synonyms", "synonyms_en.txt", "ignoreCase", "true")
                .addTokenFilter("lowercase").addTokenFilter("stop").addTokenFilter("porterstem").build();

        Analyzer customeAnalyzer = CustomAnalyzer.builder().withTokenizer("standard").addTokenFilter("lowercase").build();

        Map<String,Analyzer> analyzerMap=new HashMap<>();
        analyzerMap.put("artist",customeAnalyzer);
        analyzerMap.put("title",customeAnalyzer);
        analyzerMap.put("text",standardAnalyzer);

        PerFieldAnalyzerWrapper analyzer = new PerFieldAnalyzerWrapper(standardAnalyzer, analyzerMap);
        IndexWriterConfig config=new IndexWriterConfig(analyzer);
        this.indexWriter=new IndexWriter(fsDirectory,config);
    }

    private void addDoc(String artist,String title,String link,String text) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("artist",artist, Field.Store.YES));
        doc.add(new StringField("link",link,Field.Store.YES));

        FieldType textType = new FieldType();
        textType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        textType.setStoreTermVectors(true);
        textType.setStoreTermVectorPositions(true);
        textType.setStoreTermVectorOffsets(true);
        textType.setStored(false);
        Field textField = new Field("text", text, textType);
        doc.add(textField);

        FieldType titleType = new FieldType();
        titleType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        titleType.setStoreTermVectors(true);
        titleType.setStoreTermVectorPositions(true);
        titleType.setStoreTermVectorOffsets(true);
        titleType.setStored(true);
        Field titleField = new Field("title",title, titleType);
        doc.add(titleField);

        this.indexWriter.addDocument(doc);
    }

    public void writeDirectory() throws IOException, CsvValidationException {
        if(checkDirectory()){
            createIndexWriter();

            String[] record;

            while((record=reader.readNext())!=null) addDoc(record[0],record[1],record[2],record[3]);

            reader.close();
            this.indexWriter.close();
        }
    }
}