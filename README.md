# meta-fair
META-FAIR related code (now only for quantitative analysis

The API calls to portals and other FAIR evaluator services are coded in python (for easy JSON handling) and the OWL metadata handling is coded in JAVA (for using the power of OWL-API). The entry point is python, however, please make sure to start the java program before running python. 

Java main runnable: fr.enit.pics.App.java (if you run the main method then it will start a server)

Some rudimentary Python classes are created but the runnable code is in jupyter notebook driver.ipynb. Program 1 will save the original and curated source files at \src\test\resources\ folder for a given ontology acronym e.g., "AGRO". It is easy to extend it for a list of ontologies or all ontologies from a portal. 
(Please pass your ACCESS_TOKEN for the portal you are accessing)

Further work:
- need to run the original and curated files through FAIR evaluators APIs
- Classify the files according to minimum, moderate, extended FAIR data.
