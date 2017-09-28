#Beschrijf welke functionaliteiten binnen jullie projectapplicatie beschikbaar zijn.
#Beschrijf op welke wijze deze getest kunnen worden.
#Schrijf code voor het testen van een functionaliteit. Met een test moet worden gevalideerd:
#    - Of de outputs voldoen aan de schema’s.
#    - Of de applicatie functioneel werkt.
#    - Of foutieve inputs ook leiden tot fouten. (fail-test)
import requests
from voluptuous import Schema
from voluptuous import Any

def get_articles(id):
    r = requests.get("http://imready.ml:8080/blocks/" + id)
    return r.json()
def get_all_articles():
    r = requests.get("http://imready.ml:8080/blocks/")
    return r.json()
	
	
block = {
    'id': str,
    'name': str,
    'description': str,
    'activities':[{
        'name': str,
        'description': str,
        'status': Any(None, str)
    }]
}
schema = Schema(block, required=True)
schema2 = Schema([block], required=True)
try:
    schema(get_articles("59c7c8577cb48f2854eff501"))
    schema2(get_all_articles())
    print("Alles lekker")
except MultipleInvalid as e:
    print(e)