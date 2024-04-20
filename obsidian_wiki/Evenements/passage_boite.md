@description
Evenement de choix de role. La Boite arrive à un joueur, il choisit un role et la boite repart.
**La Boite possède un nombre fini de jetons et pierres, chaque rôle choisi enlève le jeton correspondant ou les pierres correspondantes dans la boite, à l'exception des enfants des rues**.
On peut choisir le rôle enfant des rues si la boite est vide ou si on est le dernier joueur de l'ordre de passage.

Roles : via superclasse [abstract Role](obsidian_wiki/Roles/abstract_role.md).

@time
Debut à 10 minutes.

@commandes
/mf choix <jeton/pierres/edr> <x/null>
Si choix jeton, le joueur prend le rôle x. Si choix pierres, le joueur prend x pierres dans la boite.
Si rien n'est renseigné en x, il choisit aléatoirement le role dans le cas de jeton et prend une pierre dans le cas de pierres.
Si choix edr, le joueur prend le role enfant des rues.

/mf cacher x
Permet d'enlever sans le choisir, le role x de la partie. (fonctionne uniquement avec des jetons.) Ne fonctionne qu'en 1er joueur de l'ordre du passage.

-> Erreur InvalidRoleException si rôle indisponible / trop peu de pierres / enfant des rues impossible à prendre / impossible de cacher le role.