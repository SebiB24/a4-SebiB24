# Magazin Electronice

Aceasta aplicație gestionează un magazin electronic, permițând utilizatorilor să adauge, să actualizeze și să șteargă produse și comenzi. Aplicația dispune de o interfață grafică realizată cu JavaFX.

## Structura Proiectului

- **`Domain`**: Conține clasele de bază pentru entitățile aplicației, cum ar fi `Produs` și `Comanda`.
- **`Repository`**: Contine clasele cu scop de creare si gestionare a listelor de produse si comenzi.
- **`Service`**: Implementează logica aplicației și interacționează cu datele.
- **`MagazinElectronice`**: Clasa principală care gestionează interfața grafică și interacțiunile utilizatorului.

## Funcționalități

- Gestionarea produselor: 
  - Adăugare, ștergere și actualizare produse
  - Vizualizare produse într-un tabel
  - Sortare produse după încasări
- Gestionarea comenzilor:
  - Adăugare, ștergere și actualizare comenzi
  - Vizualizare comenzi într-un tabel
- Funcții speciale:
  - Afișare categorii cu numărul de produse comandate
  - Cele mai profitabile luni

## Cum se utilizează

1. Rulați aplicația și veți fi întâmpinat de ecranul de gestionare a produselor.
2. Puteți naviga între secțiuni folosind butonul de switch din dreapta sus.
3. Selectați un element din tabel pentru a-l edita sau șterge.
4. Utilizați butoanele speciale pentru a vedea date suplimentare despre vânzări.

## Tehnologii utilizate

- Java
- JavaFX
- Streams
- Random
- SQLite

## Informații suplimentare

Această aplicație este un proiect demonstrativ și poate fi extinsă pentru a include o bază de date pentru stocarea persistentă a informațiilor.

## Capturi de ecran din aplicatie

![image](https://github.com/user-attachments/assets/7500129b-8399-41f6-ac73-83ee9c41fb1b)

![image](https://github.com/user-attachments/assets/a35ee491-fddb-4cb3-85c8-763cc04dfbc8)




