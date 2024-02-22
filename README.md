# Projet GLO4002 : Les rongeurs vers la GLOire

Nous voilà dans un monde fictif où rongeurs (hamsters[acteur], rats[avocat] et chinchillas[agents]) gravitent dans le monde du cinéma).

Ceci est un jeu du style RPG(Role Playing Game) où les rongeurs devront soigner leur réputation, faire des alliances afin de mousser leur carrière et rester
dans le jeu.

## Démarrer le jeu

Vous pouvez démarrer le serveur (`GameServer`).

Le `main()` ne demande pas d'argument.

Vous pouvez rouler le serveur via maven:

```bash
mvn clean install
mvn exec:java -pl game
```

## MEP 1

- ### US1 : TURN - Tour et reset
  - le jeu doit permettre l’accumulation d’événements. Pour l’instant, il n’existe aucun événement, mais ils vont apparaître prochainement. 
  - Lorsque votre application recevra une requête /turn, vous devez activer le tour en exécutant tous les événements accumulés dans l’ordre reçu.
  - Il doit être possible de reset le jeu, ce qui veut dire que tout ce qui aura été créé/ajouté devra être effacé.


- ### US2 : RON - ajouter des personnages et réseaux sociaux
    - Dans le jeu, trois types de rongeur peuvent etre ajoutés : hamster, rat et chinchilla.
    - En ajoutant un personnage, vous devez spécifier son nom (le nom ne peut etre suporté que par un seul rongeur), son type, et le salaire qu’il gagnera (salaire > 0).
    - Les personnages n’entrent dans le jeu qu’après un POST à /turn dans l’ordre chronologique d’ajout.
    - Le pointage de réputation est initialement à 75 et le solde du compte en banque à 1000.
    - Dès leur entrée dans le jeu, les hamsters et les chinchillas doivent s’ouvrir un compte sur Hamstagram. Leur nom d’usager sur ce réseau social est le même que leur nom. De plus, dès leur inscription, les personnages ont d’emblée 10 000 abonnés.
  

- ### Réglement :  
  un rongeur meurt si :
  - réputation < 15pts OU
  - followers < 1000 OU
  - bankBalance <= 0


- ### Requête :
  - activer un tour *`POST /turn`*
    Réponse : HTTP 200 Ok
    ```json
    {
      "turnNumber": 1::int
    }
    ```

  - reset la partie *`POST /reset`*  
    Réponse : HTTP 200 Ok
  
  - ajouter un personnage *`POST /characters`*   
    ```json
    {
      "name": "Bob",
      "type": "hamster",
      "salary": 100
    }
    ```
    Réponse : HTTP 200 Ok  

    Exceptions :  
    Si le type de rongeur est invalide.  
    HTTP 400 Bad Request
    ```json
    {
      "error": "INVALID_TYPE"::string,
      "description" : "Invalid type."::string
    }
    ```
    Si le salaire est <= 0.  
    HTTP 400 Bad Request
    ```json
    {
      "error": "INVALID_SALARY"::string,
      "description" : "Salary must be > 0."::string
    }
    ```

  - Obtenir les informations une fois le personnage dans le jeu *`GET /characters/{name}`*
    Réponse 
    ```json
    {
      "name": ""::string,
      "type": hamster | rat | chinchilla,
      "reputationScore": 75::int,
      "bankBalance": 1000::int
    }
    ```
    Exception  
    Si le nom du rongeur n’existe pas.
    HTTP 404 Not Found
    ```json
    {
      "error": "CHARACTER_NOT_FOUND"::string,
      "description" : "Character not found."::string
    }
    ```
    
  - Réseau social *`GET /hamstagram/<userid>`*
    Réponse
    ```json
    {
      "username": ""::string,
      "nbFollowers": 10000::int
    }
    ```
    Exception  
    Si le id du rongeur n’existe pas.  
    HTTP 404 Not Found
    ```json
    {
      "error": "CHARACTER_NOT_FOUND"::string,
      "description" : "Character not found."::string
    }
    ```

    
- ### Exemples:

      - Exemple 1 - Ajout de personnage:

        POST /characters => Ajout de Bob.  
        GET /characters/Bob => Not found car le tour n’a pas encore été joué.  
        POST /turn  
        GET /characters/Bob => Retourne les informations de Bob.
      
      - Exemple 2 - Ajout de personnage avec le même nom qu’un autre:

        POST /characters => Ajout de Bob.  
        POST /turn  
        POST /characters => Ajout de Bob.  
        POST /turn => L’ajout du 2e rongeur Bob sera ignoré.  
      
      - Exemple 3 - Ajout de personnage avec le même nom qu’un autre:  

        POST /characters => Ajout de Bob.  
        POST /characters => Ajout de Bob.  
        POST /turn => L’ajout du 2e rongeur Bob sera ignoré.  

### Source  
https://projet2023.qualitelogicielle.ca/
