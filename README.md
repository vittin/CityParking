## Todo:

1. Testy API
2. comment's `todo://`

## Endpoints

### Client:

```
/api/v1/customer`
    /{id} - GET(1), POST(2)
    /{id}/park/{eventId} - GET(3), PUT(4)
    
    (1) - return actual summary cost for all non-paid parking events
    (2) - start new parking event, returns park event
    (3) - return park event
    (4) - send request for charge account and stop parking, return park event 
```

### Owner:

```
/api/v1/owner
   /customer/{id} - GET(1), POST(2), PUT(3), DELETE(4)
   /park/{eventId} - GET(5), PUT(6), DELETE(7)
   /status/{date} - GET(8)
   
   //todo: tbc
```

## Założenia

1. Każdy użytkownik ma unikalny username (identity), który *MOŻNA* zmieniać.
2. Każdy użytkownik *MOŻE* parkować na wielu miejscach parkingowych równocześnie
3. Każde parkowanie *MUSI* być rozliczone dokładnie jedną walutą
4. Każde parkowanie *NIE MOŻE* być rozliczone więcej niż raz
5. Każde parkowanie *MUSI* być rozliczone przez użytkownika parkującego
6. Status użytkownika `vip` *MUSI* zostać nadany przez właściciela systemu
7. Client *NIE POWINIEN* wysyłać requesta o zakończenie parkowania więcej niż raz
8. Client *POWINIEN* sprawdzić czy zakończenie parkowania zakończyło się poprawnie
(`park.price.paid=true && Not.nullValue(park.endDate)`)

## Słownictwo

STANDARD | PL
---------|---
MUST | MOŻE, MUSI
SHOULD, MAY | POWINNO
SHOULD NOT, MAY NOT | NIE POWINNO
CAN NOT | NIE MOŻE
