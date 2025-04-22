# Information Retrieval Project – Lucene Search Engine

## Description
This repository contains the Information Retrieval project developed for the course **MYY003: Information Retrieval** at the University of Ioannina (Spring Semester 2023).

The objective was to implement a search engine using **Apache Lucene**, allowing users to explore a large collection of song data (titles, artists, lyrics, and links) with various search functionalities. The project focused on indexing, efficient querying, and semantic search features.

## Features
- **Document Collection:**  
  44,824 unique songs from 643 artists (sourced from Kaggle Spotify Million Song Dataset).
- **Indexing:**  
  Built a Lucene inverted index with custom analyzers for titles and artists.
- **Supported Queries:**  
  - Term Query  
  - Phrase Query  
  - Wildcard Query  
  - Prefix Query  
  - Fuzzy Query  
  - Boolean Query (AND, OR, NOT)
- **Search Interface (Swing GUI):**  
  - Term-based and semantic (embedding) search.
  - Query suggestions based on search history.
  - Highlighted matching keywords in results.
  - Incognito search (no history stored).
  - Grouped display of search results.
- **Semantic Search:**  
  Integrated word embeddings to find similar terms.
- **Search History:**  
  Automatic logging of search queries (optional).

## Technologies Used
- **Java 17+**
- **Apache Lucene**
- **Swing (GUI)**
- **Pre-trained Word Embeddings** for semantic search.

## How to Run
1. Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse).
2. Compile the Java files.
3. Run the `SearchEngine.java` main class.
4. Use the GUI to perform searches on the song database.

## Authors
- **Sidiropoulos Georgios** (cs04789@uoi.gr)
- **Theodoridis Charalampos** (cs04674@uoi.gr)
