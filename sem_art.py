import json
import requests

class Semart:
    def __init__(self, token, onto) -> None:
        self.token = token
        self.onto = onto
        self.__load()

    def __load(self):
        response = requests.get(self.token.url+"/ontologies/"+self.onto+'/latest_submission'+'?display=all&apikey='+self.token.auth)
        print("loading all metadata from " + self.token.url+"/ontologies/"+self.onto+'/latest_submission'+'?display=all&apikey='+self.token.auth)
        self.data = json.loads(response.text)    
    
    def get_source(self):
        print("loading all metadata from " + self.token.url+"/ontologies/"+self.onto+'/download?apikey='+self.token.auth)
        return self.token.url+"/ontologies/"+self.onto+'/download?apikey='+self.token.auth
    
    def get_metadata(self):
        self.metadata = self.data['@context']
        meta = []
        for m in self.metadata:
            if(m not in ["@vocab", "metrics", "ontology", "submissionStatus"]):
                meta.append(m)
        return meta
    
    def get_metadata_value(self, md):
        self.metadata = self.data['@context']
        t = (self.metadata[md], self.data[md])
        return t
