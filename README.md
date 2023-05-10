
# Kuba

## Noms alternatifs

Traboulet, Akiba

## Nombre de joueurs

Deux (1 vs 1 ou 1 vs IA)

## Équipement

Une grille carrée kxk ou k = 4*n-1 pour un certain n > 0, 4*n<sup>2</sup> pions noirs et blancs et 8*n<sup>2</sup>-12*n+5 pions rouges neutres sont nécessaires pour jouer. Les pions sont décrits comme des « billes » pour ce jeu.

## Histoire

Kuba a été inventé en 1997 par Patch Products.

## Objectif

Un joueur gagne en repoussant et en capturant la moitié des pierres rouges neutres ou en repoussant toutes les pierres adverses. Un joueur qui n'a pas de coups légaux disponibles a perdu la partie.

## Jouer

Avec des tours alternés, les joueurs déplacent une seule bille dans n'importe quelle direction orthogonale. Cependant, pour faire glisser une bille, il faut y avoir accès. Par exemple, pour faire glisser une bille vers la gauche, la cellule juste à droite si elle doit être vacante. S'il y a d'autres billes; les vôtres, ceux de votre adversaire ou les rouges neutres ; dans la direction de votre mouvement à la cellule vers laquelle vous vous déplacez, ces billes sont poussées d'une cellule vers l'avant le long de l'axe de votre mouvement. Jusqu'à six billes peuvent être poussées par votre seule bille à votre tour. Bien qu'un joueur ne puisse pas pousser l'une de ses propres billes, tous les compteurs adverses qui sont repoussés sont retirés du jeu et tous les compteurs neutres qui sont repoussés sont capturés par le joueur qui pousse pour être ajoutés à son stock de billes rouges neutres capturées. . Si vous parvenez à repousser une bille neutre ou adverse, vous avez droit à un autre tour.

Kuba incorpore la règle Ko pour interdire que la même position soit répétée encore et encore.

## Contrôleur

Tous les types de contrôleurs proposés sont décrit dans la page settings qui apparait en cliquant sur le bouton settings en haut à droite de l'écran d'accueil du jeu.

## Lancer le jeu

Pour pouvoir lancer le jeu sous linux, exécutez le script exec.sh après lui avoir donné les droits d'exécutions:

``` bash
    chmod u+x exec.sh
    ./exec.sh
``` 

Sinon sous Windows, tapez les commandes suivantes sur le cmd:

``` cmd
    javac -classpath src/ src/Main.java
    java -classpath src/ Main
```

Pour essayer le mode en ligne (juste en localHost), il faut lancer le script online.sh deux fois chacun modélise un joueur

``` bash
    chmod u+x online.sh
    ./online.sh
```








