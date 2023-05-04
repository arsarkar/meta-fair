package fr.enit.pics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFactoryNotFoundException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.RemoveOntologyAnnotation;
import org.semanticweb.owlapi.model.UnloadableImportException;

import py4j.GatewayServer;

/**
 * OWL annotation property manager
 *
 */
public class App 
{
    private OWLOntology o;
    private Map<String, String> unloadableOntology = new HashMap<String, String>();
    
    public static void main( String[] args )
    {
        GatewayServer gatewayServer = new GatewayServer(new App());
        gatewayServer.start();
        System.out.println("Java APP Gateway Started!");
    }

    public OWLOntology getOntology(){
        return o;
    }

    /**
     * load an ontology from an URL
     * @param uriString
     * @return
     */
    public void loadOntology(String uriString){
        IRI ir =  IRI.create(uriString.trim());
        OWLOntology ontology = null;
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntologyLoaderConfiguration config = new OWLOntologyLoaderConfiguration();
        config.setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT);
        manager.setOntologyLoaderConfiguration(config);
        try {
            System.out.println("INFO::Loading ontology from " + uriString);
            ontology = manager.loadOntologyFromOntologyDocument(ir);
        } catch (UnloadableImportException e){
            saveUnloadableOntology(uriString, e.getMessage());
        } catch (OWLOntologyCreationException e) {
            saveUnloadableOntology(uriString, e.getMessage());
        } catch (OWLOntologyFactoryNotFoundException e){
            saveUnloadableOntology(uriString, e.getMessage());
        }
        this.o = ontology;
    }

    /**
     * write cause of failure to load ontology
     * @param uriString
     * @param messString
     */
    private void saveUnloadableOntology(String uriString, String messString) {
        System.out.println("WARNING!: " + messString);
        unloadableOntology.put(uriString, messString);
    }

    private void writeUnloadableOnotlogies(Map<String, String> unloadableOntology2, String path) throws IOException {
        FileWriter csvWriter = new FileWriter(path);
        for(String k: unloadableOntology.keySet()){
            String message = unloadableOntology.get(k).split("\n")[0];
            csvWriter.write(k + "," + message + "\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }

    /**
     * Delete all metadata annotations from the given ontology
     * @param o Ontology
     * @return OWLOntology
     */
    public void deleteAnnotations(){
        OWLOntologyManager man = this.o.getOWLOntologyManager();
        this.o.annotations().forEach(a->{
            RemoveOntologyAnnotation ra = new RemoveOntologyAnnotation(this.o, a);
            man.applyChange(ra);
        });
    }

    /**
     * Add the given metadata and metadata value to the given ontology
     * @param metadata metadata IRI
     * @param metadataValue metadata value
     * @return OWLOntology
     */
    public void addAnnotations(String metadata, String metadataValue){
        OWLOntologyManager man = o.getOWLOntologyManager();
        OWLDataFactory df = man.getOWLDataFactory();
        AddOntologyAnnotation an = new AddOntologyAnnotation(o, df.getOWLAnnotation(df.getOWLAnnotationProperty(metadata), 
                            df.getOWLLiteral(metadataValue)));
        man.applyChange(an);
    }
    
    /**
     * Save the ontology to the given path
     * @param o Ontology
     * @param path file path
     */
    public String saveOntology(String path){
        try {
            o.getOWLOntologyManager().saveOntology(o, new FileOutputStream(new File(path)));
        } catch (OWLOntologyStorageException | FileNotFoundException e) {
            return e.getMessage();
        }
        return "Successfully saved ontology!";
    }
}
