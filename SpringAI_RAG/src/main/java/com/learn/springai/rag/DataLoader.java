package com.learn.springai.rag;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

// @Component
public class DataLoader {

    private final VectorStore vectorStore;  

    public DataLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }   

    //  @PostConstruct
    public void LoadDataIntoVectorStore() {
        // 50 Spring AI related sentences 
        // 25 sentences on Bangalore
        List<String> sentences = List.of(
            "Spring AI simplifies the integration of AI capabilities into Spring applications.",
            "Vector stores are essential for efficient retrieval of similar data points in AI applications.",
            "Spring AI provides seamless support for various machine learning models.",
            "Using Spring AI, developers can easily build intelligent applications.",
            "Vector embeddings help in representing data in a format suitable for AI processing.",
            "Spring AI supports natural language processing tasks out of the box.",
            "With Spring AI, you can integrate chatbots into your applications effortlessly.",
            "Spring AI offers tools for building recommendation systems.",
            "The framework allows for easy deployment of AI models in cloud environments.",
            "Spring AI's modular architecture makes it easy to extend and customize.",
            "Developers can leverage Spring AI to create predictive analytics solutions.",
            "Spring AI integrates well with other Spring ecosystem projects.",
            "The framework provides robust support for data preprocessing and feature extraction.",
            "Spring AI enables real-time data processing for dynamic applications.",
            "You can use Spring AI to build intelligent search engines.",
            "Spring AI's vector store capabilities enhance information retrieval tasks.",
            "The framework supports various data formats for input and output.",
            "Spring AI allows for easy experimentation with different machine learning algorithms.",
            "With Spring AI, you can build applications that learn from user interactions.",
            "The framework provides comprehensive documentation and community support.",
            "Spring AI facilitates the development of computer vision applications.",
            "You can use Spring AI to analyze large datasets efficiently.",
            "The framework supports integration with popular AI libraries and tools.",
            "Spring AI helps in building scalable AI solutions for enterprise applications.",
            "The vector store in Spring AI optimizes storage and retrieval of high-dimensional data.",
            "Spring AI provides built-in functionalities for model training and evaluation.",
            "You can create custom AI workflows using Spring AI's flexible architecture.",
            "The framework supports multi-language applications with ease.",
            "Spring AI enables developers to focus on building features rather than infrastructure.",
            "With Spring AI, you can implement advanced analytics capabilities in your apps.",
            "The framework offers tools for visualizing AI model performance.",
            "Spring AI's vector store is designed for high availability and reliability.",
            "You can leverage Spring AI to build intelligent automation solutions.",
            "The framework supports continuous integration and deployment of AI models.",
            "Spring AI provides mechanisms for monitoring and managing deployed models.",
            "You can use Spring AI to enhance user experiences with personalized content.",
            "Bangalore is known as the Silicon Valley of India due to its thriving tech industry.",
            "The city boasts a pleasant climate throughout the year, making it a comfortable place to live.",
            "Bangalore is home to numerous prestigious educational institutions and research centers.",
            "The city has a vibrant cultural scene with many theaters, art galleries, and music festivals.",
            "Bangalore's cuisine is diverse, offering everything from traditional South Indian dishes to international flavors",
            "Bangalore has a well-developed public transportation system, including buses and a metro network.",
            "The city is surrounded by beautiful parks and gardens, providing ample green spaces for residents."
            ,"Bangalore hosts several major tech conferences and events, attracting professionals from around the world.",
            "The city has a rich history, with landmarks such as Bangalore Palace and Tipu Sultan's Summer Palace.",
            "Bangalore is a hub for startups and innovation, fostering a dynamic entrepreneurial ecosystem.",
            "The city experiences a mix of modern and traditional architecture, reflecting its growth over the years",
            "Bangalore is known for its friendly and cosmopolitan population, with people from various cultures living together.",
            "The city has a thriving nightlife, with numerous pubs, clubs, and restaurants to explore.",
            "Bangalore's IT parks, such as Electronic City and Whitefield, are home to many global tech companies.",
            "The city offers a range of recreational activities, including hiking trails and weekend getaways to nearby hill stations."
        );

        List<Document> documents = sentences.stream().map(Document::new).collect(Collectors.toList());
        vectorStore.add(documents);
    }

}
