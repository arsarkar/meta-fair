package fr.enit.pics;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.Test;
import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.RemoveOntologyAnnotation;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void readAnnotations()
    {
        App a = new App();
        a.loadOntology("http://data.agroportal.lirmm.fr/ontologies/AGRO/download?apikey=<>"); //replace <> with portal key
        a.getOntology().annotations().forEach(an->{
            an.annotationPropertiesInSignature().forEach(s->{
                System.out.println(s.toString());
            });
            System.out.println(an.annotationValue().toString());
        });
        System.out.println(a.getOntology().toString());
    }

    @Test
    public void deleteAnnotations()
    {
        App a = new App();
        a.loadOntology("http://data.agroportal.lirmm.fr/ontologies/AGRO/download?apikey=<>"); //replace <> with portal key
        OWLOntologyManager man = a.getOntology().getOWLOntologyManager();
        a.getOntology().annotations().forEach(an->{
            RemoveOntologyAnnotation ra = new RemoveOntologyAnnotation(a.getOntology(), an);
            man.applyChange(ra);
        });
        try {
            man.saveOntology(a.getOntology(), new FileOutputStream(new File("src/test/resources/agro.owl")));
        } catch (OWLOntologyStorageException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void addAnnotations()
    {
        App a = new App();
        a.loadOntology("http://data.agroportal.lirmm.fr/ontologies/AGRO/download?apikey=<>"); //replace <> with portal key
        OWLOntologyManager man = a.getOntology().getOWLOntologyManager();
        OWLDataFactory df = man.getOWLDataFactory();
        AddOntologyAnnotation an = new AddOntologyAnnotation(a.getOntology(), df.getOWLAnnotation(df.getRDFSComment(), 
                            df.getOWLLiteral("Arko added this comment", "en")));
        man.applyChange(an);
        try {
            man.saveOntology(a.getOntology(), new FileOutputStream(new File("src/test/resources/agro1.owl")));
        } catch (OWLOntologyStorageException | FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
