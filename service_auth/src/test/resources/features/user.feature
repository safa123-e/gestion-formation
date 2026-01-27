# language: fr
Fonctionnalité: Gestion des utilisateurs
  En tant qu'administrateur
  Je veux gérer les utilisateurs
  Afin de maintenir la base de données

  Scénario: Créer un nouvel agent
    Etant donné une base de données vide
    Quand je crée un agent "Ahmed" avec l'email "ahmed@email.com"
    Alors l'agent "Ahmed" existe dans la base

  Scénario: Lister tous les agents
    Etant donné les agent suivants existent:
      | idAgentGa | nomPrenomAgentGa | emailAgentGa     | isIsieGa | matricule | cinAgentGa
      | 1         | Ahmed            | ahmed@email.com  |   true   | 0000      | 12345678
      | 2         | Fatma            | fatma@email.com  |   true   | 0000      | 12345879
    Quand je demande la liste des agents
    Alors je reçois 2 agents

  Scénario: Supprimer un agent
    Etant donné un agent "Ahmed" existe avec l'ID 1
    Quand je supprime l'agent avec l'ID 1
    Alors l'agent avec l'ID 1 n'existe plus