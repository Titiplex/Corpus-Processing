package main;
import java.io.FileNotFoundException;
import java.util.Scanner;

import base.*;
import moteurRechercheException.Bm25Exception;
import moteurRechercheException.CorpusException;
import moteurRechercheException.TfIdfException;
import traitement.*;

public class Test {
	public static void main(String[] args) {
		
		Corpus ouvrage;
		try {
			ouvrage = new Corpus("booksummaries.txt", DataSets.Ouvrage);
			Scanner scanner = new Scanner(System.in);
			
			System.out.println("Entrer votre requete de recherche : ");
			String query = scanner.nextLine();
			System.out.println("Entrez le nombre de docs a afficher : ");
			int number = scanner.nextInt();
			
			scanner.close();
			
			// System.out.println(ouvrage.toString());
			TfIdf tfidf = new TfIdf();
			Bm25 bm25 = new Bm25();
			
			tfidf.processCorpus(ouvrage);
			bm25.processCorpus(ouvrage);
			
			System.out.println("\n/!\\ TDIDF /!\\");
			tfidf.processQuery(query, number);
			System.out.println("\n/!\\ BM25 /!\\");
			bm25.processQuery(query, number);
			// System.out.println("\nTaille du vocabulaire : " + Vocabulary.getIdMotCollectionSize());
			// System.out.println("Mémoire utilisée : " + Runtime.getRuntime().totalMemory() / (1024 * 1024) + " Mo");

		} catch (FileNotFoundException | CorpusException | TfIdfException | Bm25Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
