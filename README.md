# GECS
Grammar Errors Correction Sistem 
progetto per Sistemi Intelligenti per Internet 

- Tools utilizzati:
-
- IDE

 Eclipse (Mars Java EE developers) (https://projects.eclipse.org/releases/mars)


- Per l’individuazione del linguaggio

 TextCat 1.1(http://sourceforge.net/projects/textcat/files/OldFiles/textcat-1.1.jar/download)


- Dizionari

 enWiki -> titoli pagine di wikipedia (https://dumps.wikimedia.org/enwiki/latest/enwiki-latest-all-titles.gz)


languageTool -> servizio API e dizionari(scaricare qui la versione stand alone https://www.languagetool.org/)



- SVM

Weka

- Altro

apache commons lang (https://commons.apache.org/proper/commons-lang/download_lang.cgi, scaricare il file binaries)

stanford parser (http://nlp.stanford.edu/software/stanford-parser-full-2015-12-09.zip  e   stanford-english-corenlp-2016-01-10-models.jar)


-apache commons codec (https://commons.apache.org/proper/commons-codec/download_codec.cgi)


-XML Parser (http://www.java2s.com/Code/Jar/x/DownloadxmlParserAPIs202jar.htm)

—————————————————————————————————
- Setup del progetto:
- 
- Aprire il progetto con Eclipse: file-> import-> Git->Projects from Git-> Existing local repository->add(path della repository precedentemente clonata in locale)->”repository name”-> Next-> selezionare il progetto

- modificare il nome del package nella classe in cui da errore (bisogna aggiungere src.)

- cliccare sul package che continua a dare errore e cliccare sul suggerimento Configure Build path

- nella finestra che si apre cliccare ok

- importare le jar esterne: Project -> Properties -> Libraries -> add external JARs 

- importare anche la cartella org contenuta in languageTools: Project -> Properties -> Libraries -> add external class folder

———————————
- Esecuzione: 
- 
- Per far partire l’analisi real time: *Run.java*
- Per far partire l’analisi batch: *Starter.java*


