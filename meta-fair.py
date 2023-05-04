import argparse

parser = argparse.ArgumentParser(description = "Meta-FAIR is a tool to evaualte ontology artifacts with multiple FAIR evaluators.")

parser.add_argument("tool", nargs = '*', metavar = "tool", type = str,
                     help = "1st argument = ofaire/foops/fair-checker, 2nd argument = uri of the ontology (Acronym if ofaire is used), 3rd argument (optional) = config file path if portal is used")

args = parser.parse_args()

if args.tool[0] == 'ofaire':
    from ofaire import OFaire
    from access_token import AccessToken
    t = AccessToken(args.tool[2])
    if t.url == 'http://data.agroportal.lirmm.fr/':
        portal = 'agroportal'
    elif t.url == 'http://services.industryportal.enit.fr/':
        portal = 'industryportal'
    elif t.url == 'https://services.bioportal.lirmm.fr/':
        portal = 'bioportal'    
    xt = OFaire(portal,t)
    xt.check(args.tool[1])
    print('OFaire result: \n' +xt.result)    
elif args.tool[0] == 'foops':
    from foops import Foops
    xt = Foops()
    xt.check(args.tool[1])
    print('Foops result: \n' +xt.result)   
elif args.tool[0] == 'fair-checker':
    from fchecker import Fcheker
    xt = Fcheker()
    xt.check(args.tool[1])
    print('FAIR-Checker result: \n' +xt.result)  