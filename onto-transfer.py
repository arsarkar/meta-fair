import argparse

parser = argparse.ArgumentParser(description = "Onto-trans is a program to read ontologies from portals and annotate them with curated metadata.")

parser.add_argument("portal", nargs = '*', metavar = "portal", type = str,
                     help = "1st argument = file path to save ontologies, 2nd argument = acronyms of the ontologies (comma separated), 3rd argument = config file path if portal is used")

args = parser.parse_args()

from access_token import AccessToken
t = AccessToken(args.portal[2])
if t.url == 'http://data.agroportal.lirmm.fr/':
    portal = 'agroportal'
elif t.url == 'http://services.industryportal.enit.fr/':
    portal = 'industryportal'
elif t.url == 'https://services.bioportal.lirmm.fr/':
    portal = 'bioportal' 

from access_token import AccessToken
t = AccessToken(args.portal[2])

from py4j.java_gateway import JavaGateway
gateway = JavaGateway()

ontos = args.portal[1].split(',')

for oname in ontos:
    # read the original ontology file
    from sem_art import Semart 
    s = Semart(t, oname)
    gateway.loadOntology(s.get_source())
    # save the original ontology
    st = gateway.saveOntology(args.tool[0]+oname+"-orig.owl")
    print(st)
    # remove all original metadata annotations
    gateway.deleteAnnotations()
    # get all metadata and annotate
    for m in s.get_metadata():
        ms = s.get_metadata_value(m)
        # ignore if no value is curated for the metadata
        if(ms[1]==None): 
            continue
        # for 'contacts' only pick up the name    
        if(m=="contacts"):
            if(len(ms[1])==0):
                continue
            for mi in ms[1]:
                print(str(ms[0]))
                print(str(mi['name']))
                gateway.addAnnotations(str(ms[0]), str(mi['name']))
            continue
        # for tuple or list metadata value assert annotations separately for each member
        if type(ms[1]) is list or type(ms[1]) is tuple:
            if(len(ms[1])==0):
                continue
            for mi in ms[1]:
                gateway.addAnnotations(str(ms[0]), str(mi))
            continue
        # general singleton metadata 
        gateway.addAnnotations(str(ms[0]), str(ms[1]))
    # save the curated ontology
    st = gateway.saveOntology(args.tool[0]+oname+"-curated.owl")    