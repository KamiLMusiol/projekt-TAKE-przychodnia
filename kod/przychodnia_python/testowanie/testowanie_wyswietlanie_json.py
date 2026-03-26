import json



def show_as_json(nazwa, dane):
    print(f"\n{'='*20} {nazwa.upper()} {'='*20}")

    print(json.dumps(dane, indent=4, ensure_ascii=False)) #dump strign zmienia nasz tekst na format json po każdym wcięciu 4 spacje ensure_ascii=False bo inaczej nei wykryje ł

