package fr.campusetfocus.game.cell;

import fr.campusetfocus.being.Being;
import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.exception.PlayerLostException;
import fr.campusetfocus.exception.PlayerMovedException;
import fr.campusetfocus.game.Cell;
import fr.campusetfocus.being.Enemy;
import fr.campusetfocus.game.Dice;
import fr.campusetfocus.menu.Menu;


public class EnemyCell extends Cell {
    private Enemy enemy;

    public EnemyCell(int position, Enemy enemy) {
        super(position, CellType.ENEMY);
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    @Override
    public void interact(Menu menu, GameCharacter player, Dice dice) throws PlayerLostException, PlayerMovedException {
        if (enemy == null) {
            menu.display("Il n'y a plus d'ennemi ici, vous poursuivez votre tour.");
        } else {
            menu.display("Vous vous retrouvez face à un " + enemy.getName() + " !");
            menu.displayTitle("Combat !");
            this.fight(menu, player, dice);
        }
    }

/**
 * Gère un tour de combat contre un ennemi.
 * Le joueur attaque d'abord l'ennemi puis, si ce dernier survit, l'ennemi riposte.
 * Après chaque échange, la mort éventuelle de l'un des protagonistes est vérifiée.
 * Enfin, le joueur choisit de poursuivre le combat (appel récursif) ou de fuir.
 */
    private void fight (Menu menu, GameCharacter player, Dice dice) throws PlayerLostException, PlayerMovedException {
        while(menu.getChoice("Que souhaitez vous faire ?", new String[]{"Combattre", "Fuir"}) == 1) {
            int criticallity = dice.roll(1, 20);
            int blow = attack(player, enemy, criticallity);

            if (criticallity == 1) menu.display("Echec critique ! Votre coup est raté !");
            if (criticallity == 20) menu.display("Réussite critique ! Votre coup est très efficace !");
            if (blow <= 0) menu.display("Votre attaque est trop faible pour atteindre l'ennemi.");
            else {
                menu.display("Vous infligez " + blow + " dégâts à l'ennemi.");
                menu.display("Il lui reste " + enemy.getLife() + " points de vie.");
            }
            if (enemy.getLife() <= 0) {
                menu.displaySuccess("L'ennemi est mort ! Vous gagnez le combat !");
                this.empty();
                return;
            }

            criticallity = dice.roll(1, 20);
            blow = attack(enemy, player, criticallity);

            if (criticallity == 1) menu.display("Echec critique ! Votre ennemi rate son coup !");
            if (criticallity == 20) menu.display("Réussite critique ! Son coup est très efficace !");
            if (blow == 0) menu.display("L'ennemi est trop faible pour vous atteindre.");
            else {
                menu.display("L'ennemi vous inflige " + Menu.RED + blow + Menu.RESET + " dégâts.");
                menu.display("Il vous reste " + Menu.GREEN + player.getLife() + Menu.RESET + " points de vie.");
            }
            if (player.getLife() <= 0) throw new PlayerLostException("Vous avez été tué par " + enemy.getName() + " !");
        }
        throw new PlayerMovedException("Vous fuyez le combat.", -dice.roll(Math.min(6, this.getNumber()6
        )));
    }

        /**
         * Exécute une action d'attaque entre deux Being : l'attaquant tente d'infliger des dégâts
         * au défenseur en fonction de leurs attributs d'attaque et de défense. Si l'attaque de l'attaquant
         * est supérieure à la défense du défenseur, la vie du défenseur est réduite et la méthode
         * renvoie les dégâts infligés. Sinon, aucun dégât n'est infligé et la méthode renvoie 0.
         *
         * @param attacker le Being qui initie l'attaque
         * @param defender le Being qui subit l'attaque
         * @return le montant des dégâts infligés au défenseur, ou 0 si aucun dégât n'est infligé
         */
        private int attack(Being attacker, Being defender, int criticality) {
            int blow = Math.max(attacker.getAttack() - defender.getDefense(), 0);
            if (criticality == 1) blow = 0;
            if (criticality == 20) blow += 2;
            defender.changeLife(-blow);
            return blow;
        }


    @Override
    public void empty() {
        super.empty();
        enemy = null;
    }
}
