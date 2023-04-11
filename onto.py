from owlready2 import get_ontology
class Onto:
    def __init__(self, o) -> None:
        self.ontology = get_ontology(o).load
        
    def print(self):
        print(self.ontology)