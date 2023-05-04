# meta-fair
META-FAIR related code (now only for quantitative analysis

The API calls to portals and other FAIR evaluator services are coded in python (for easy JSON handling) and the OWL metadata handling is coded in JAVA (for using the power of OWL-API). The entry point is python, however, please make sure to start the java program before running python. 

Java main runnable: fr.enit.pics.App.java (if you run the main method then it will start a server).
Note: The java project is built in maven. We can use either mvn command to run App.java. We can built a target launcher later

Some rudimentary Python classes are created but the runnable code is in jupyter notebook driver.ipynb. Program 1 will save the original and curated source files at \src\test\resources\ folder for a given ontology acronym e.g., "AGRO". It is easy to extend it for a list of ontologies or all ontologies from a portal. 
(Please pass your AccessToken instant for the portal you are accessing)

AccessToken: We need a config file to be created e.g., agroportal.config. The config file will contain only two lines: one for the URL of the portal and another for the api key. This file should look like below:

```
http://data.agroportal.lirmm.fr/
the api key
```
This file needs to be passed to the constructor of AccessToken class (see the driver.ipynb)

Meta-FAIR tool to evaluate an ontology with multiple FAIR evaluators. Run the program in the parent folder by running the following with three arguments
```
python meta-fair.py 
```
1st argument = ofaire/foops/fair-checker, 2nd argument = uri of the ontology (Acronym if ofaire is used), 3rd argument (optional) = config file path if portal is used

Further work:
- Classify the files according to minimum, moderate, extended FAIR data.
