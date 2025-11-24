package com.learn.springai.rag;

import java.util.List;
import java.util.Vector;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class ProductLoader {

    private final VectorStore vectorStore;

    @Value("classpath:SmartHome_Hub_Mini.pdf")
    Resource productDataResource;

    public ProductLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadProductDataIntoVectorStore() {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(productDataResource);
        List<Document> documents = tikaDocumentReader.get();
        TextSplitter textSplitter = TokenTextSplitter
                .builder()
                .withChunkSize(100)
                .withMaxNumChunks(400)
                .build();

        vectorStore.add(textSplitter.split(documents));
    }

}
