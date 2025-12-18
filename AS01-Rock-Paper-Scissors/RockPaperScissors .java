import java.util.Scanner;
import java.util.Random;

public class RockPaperScissors {

    public static String userInput(Scanner sc){
        System.out.print(">> ");
        String userInput = sc.next();
        return userInput;
    }

    public static String welcomePlayer(Scanner sc){
        System.out.print("Enter your username ");
        String userName = userInput(sc);
        System.out.printf("Welcome, %s.\n",userName);
        return userName;
    }


    public static void mainMenu(int userCredit, String userName){
        System.out.printf("_____-----=====+++++=====-----_____\n");
        System.out.printf("               Menu               \n  !play  : Play a game!  \n  !shop  : Upgrade your gears.\n  !stats : View your current stats.\n  !rules : Learn how to play.\n  !exit  : Leave the game.\n");
        System.out.printf(" < Name: %s. | Credit: %d credits >\n", userName, userCredit);
        System.out.printf("_____-----=====+++++=====-----_____\n");
    }

    public static void userStats(String userName, int userCredit, int[] levelGear,int [] botLevelGear){
        System.out.printf("_____-----=====+++++=====-----_____\n");
        System.out.printf("            Player-Stats               \n           Rock Level <%d>.\n          Paper Level <%d>.\n       Scissors Level <%d>.\n",levelGear[0], levelGear[1], levelGear[2]);
        System.out.printf("             Bot-Stats               \n           Rock Level <%d>.\n          Paper Level <%d>.\n       Scissors Level <%d>.\n",botLevelGear[0], botLevelGear[1], botLevelGear[2]);
        System.out.printf(" < Name: %s. | Credit: %d credits >\n", userName, userCredit);
        System.out.printf("_____-----=====+++++=====-----_____\n");
        System.out.printf("                 |\n");
    }

    public static int shop(int[] levelGear, int userCredit,Scanner sc){
        //menu shop
        System.out.printf("_____-----=====+++++=====-----_____\n");
        System.out.printf("               Shop\n  !upRock     : for levelup Rock.\n  !upPaper    : for levelup Paper.\n  !upScissors : for levelup Scissors.\n");
        System.out.printf("  < Costs 100 credits per level. >\n");
        System.out.printf("       !back : Back to menu.\n       Credit: %d credits\n",userCredit);
        System.out.printf("_____-----=====+++++=====-----_____\n");
        System.out.printf("                 |\n");

        //user input
        while(true){
            String userCommand = userInput(sc);
            if(userCommand.equalsIgnoreCase("!upRock")){
                userCredit = purchase(0, userCredit, levelGear);
            } else if(userCommand.equalsIgnoreCase("!upPaper")){
                userCredit = purchase(1, userCredit, levelGear);
            } else if(userCommand.equalsIgnoreCase("!upScissors")){
                userCredit = purchase(2, userCredit, levelGear);
            } else if(userCommand.equalsIgnoreCase("!back")){
                System.out.printf("Remember: use !stats in the menu to view your gear levels.\n");
                break;
            } else {
                System.out.printf("Invalid command.\n Please check the command list on your screen.\n");
            }
        }

        return userCredit;
    }

    public static int purchase(int idxGear, int userCredit,int[] levelGear){
        int missingCredit;
        if(userCredit<100){
            missingCredit = 100-userCredit;
            System.out.printf("Purchase failed: missing < %d > credits.\n", missingCredit);
        } else {
            levelGear[idxGear]+=1;
            userCredit-=100;
            System.out.printf("Purchase successful! Current balance: < %d > credits.\n", userCredit);
        }
        return userCredit;
    }

    public static void rules(){
        System.out.printf("_____-----=====+++++=====-----_____\n");
        System.out.printf("\n               Rules\n");
        System.out.printf("| Rock beats Scissors, loses to Paper \n| Paper beats Rock, loses to Scissors \n| Scissors beats Paper, loses to Rock\n");
        System.out.printf("Each match is played as Best of 3 \n(first to win 2 rounds wins the match).\n");
        System.out.printf("\n           Draw condition\n");
        System.out.printf("If the match ends in a tie, and \nthe player's equipment level is\n at least 3 levels higher than the bot's\nit will be treated as a player win.\n");
        System.out.printf("\n               Rewards\n");
        System.out.printf("          | Win = 100 credits |\n          | Lose = nothing    |\n");
        System.out.printf("\n        Equipment upgrades\n");
        System.out.printf("The player can upgrade their equipment \nby typing !shop in the menu.\n");
        System.out.printf("_____-----=====+++++=====-----_____\n");
        System.out.printf("                 |\n");
    }

    public static int playGame(int[] levelGear, int[] botLevelGear,Scanner sc){
        int playerPoints = 0;
        int botPoints = 0;

        System.out.println("---| Chose < rock > , < paper > , < scissors > |---\n          !back : for return to menu");

        while (true){
            String userChoice = userInput(sc);
            String botChoice = botChoices();
            
            //Draw
            if(userChoice.equalsIgnoreCase("!back")){
                return 0;
            }

            if(userChoice.equalsIgnoreCase(botChoice)){
                int different;
                //ถ้ารู้ bot choice ก็จะรู้ ให้ไปเทียบ string ถ้า string == "rock" 
                if(botChoice.equalsIgnoreCase("rock")){
                    different = levelGear[0]-botLevelGear[0];
                    // กรณีที่ bot lvl เยอะกว่า
                    if(different<0 && Math.abs(different)>=3){
                        //bot win draw
                        botWin(0,0, userChoice, botChoice, levelGear, botLevelGear);
                        botPoints+=1;
                    }else if(different>0 && Math.abs(different)>=3){ // กรณีที่ player lvl เยอะกว่า
                        //player win draw
                        playerWin(0,0, userChoice, botChoice, levelGear, botLevelGear);
                        playerPoints+=1;
                    }else{
                        noPoints(0,0, userChoice, botChoice, levelGear, botLevelGear);
                    }   
                }else if(botChoice.equalsIgnoreCase("paper")){

                    different = levelGear[1]-botLevelGear[1];
                    // กรณีที่ bot lvl เยอะกว่า
                    if(different<0 && Math.abs(different)>=3){
                        //bot win draw
                        botWin(1,1, userChoice, botChoice, levelGear, botLevelGear);
                        botPoints+=1;
                    }else if(different>0 && Math.abs(different)>=3){ // กรณีที่ player lvl เยอะกว่า
                        //player win draw
                        playerWin(1,1, userChoice, botChoice, levelGear, botLevelGear);
                        playerPoints+=1;
                    }else{
                        noPoints(1,1, userChoice, botChoice, levelGear, botLevelGear);
                    }   
                }else if(botChoice.equalsIgnoreCase("scissors")){

                    different = levelGear[2]-botLevelGear[2];
                    // กรณีที่ bot lvl เยอะกว่า
                    if(different<0 && Math.abs(different)>=3){
                        //bot win draw
                        botWin(2,2, userChoice, botChoice, levelGear, botLevelGear);
                        botPoints+=1;
                    }else if(different>0 && Math.abs(different)>=3){ // กรณีที่ player lvl เยอะกว่า
                        //player win draw
                        playerWin(2,2, userChoice, botChoice, levelGear, botLevelGear);
                        playerPoints+=1;
                    }else{
                        noPoints(2,2, userChoice, botChoice, levelGear, botLevelGear);
                    }   
                }
            }else if (userChoice.equalsIgnoreCase("rock")) {
                if (botChoice.equalsIgnoreCase("scissors")) {
                    //user win
                    playerWin(2,0, userChoice, botChoice, levelGear, botLevelGear);
                    playerPoints+=1;
                } else {
                    //user lose
                    botWin(1,0, userChoice, botChoice, levelGear, botLevelGear);
                    botPoints+=1;
                }
            }else if (userChoice.equalsIgnoreCase("paper")) {
                if (botChoice.equalsIgnoreCase("rock")) {
                    //user win
                    playerWin(0,1, userChoice, botChoice, levelGear, botLevelGear);
                    playerPoints+=1;
                } else {
                    //user lose
                    botWin(2,1, userChoice, botChoice, levelGear, botLevelGear);
                    botPoints+=1;
                }
            }else if (userChoice.equalsIgnoreCase("scissors")) {
                if (botChoice.equalsIgnoreCase("paper")) {
                    //user win
                    playerWin(1,2, userChoice, botChoice, levelGear, botLevelGear);
                    playerPoints+=1;
                } else {
                    //user lose
                    botWin(0,2, userChoice, botChoice, levelGear, botLevelGear);
                    botPoints+=1;
                }
            }else{
                System.out.println("Invalid Input.");
            }

            if(playerPoints == 2){
                System.out.printf("                 |\n");
                System.out.println("Player Won 2 points first! earned 100 credits");
                System.out.printf("                 |\n");
                return 100;
            } else if(botPoints == 2){
                System.out.printf("                 |\n");
                System.out.println("Bot Won 2 points first! bot got level up random gear.");
                System.out.printf("                 |\n");
                Random random = new Random();
                switch (random.nextInt(3)+1){
                    case 1:
                        botLevelGear[0]+=1;
                        break;
                    case 2:
                        botLevelGear[1]+=1;
                        break;
                    case 3:
                        botLevelGear[2]+=1;
                        break;
                }
                return 0;
            }
        }
    }


    public static void botWin(int idxGearBot ,int idxGear,String userChoice,String botChoice, int[] levelGear,int[] botLevelGear){
        System.out.printf("Bot win! player-%s Lvl<%d> | bot-%s lvl<%d>\n",userChoice, levelGear[idxGear], botChoice, botLevelGear[idxGearBot]);
    }

    public static void playerWin(int idxGearBot ,int idxGear,String userChoice,String botChoice, int[] levelGear,int[] botLevelGear){
        System.out.printf("Player win! player-%s Lvl<%d> | bot-%s lvl<%d>\n",userChoice, levelGear[idxGear], botChoice, botLevelGear[idxGearBot]);
    }

    public static void noPoints(int idxGearBot ,int idxGear,String userChoice,String botChoice, int[] levelGear,int[] botLevelGear){
        System.out.printf("No one get Point! player-%s Lvl<%d> | bot-%s lvl<%d>\n",userChoice, levelGear[idxGear], botChoice, botLevelGear[idxGearBot]);
    }


    public static String botChoices(){
        String botChoices = "";

        Random random = new Random();
        switch (random.nextInt(3)+1){
            case 1:
                botChoices = "rock";
                break;
            case 2:
                botChoices = "paper";
                break;
            case 3:
                botChoices = "scissors";
                break;
        }return botChoices;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String userName = welcomePlayer(sc);
        int userCredit = 0;
        int[] levelGear = {0,0,0};
        int[] botLevelGear = {0,0,0};

        //array ช่อง 0 = เลเวลของ rock แล้วตามด้วย paper scissors ตามลำดับ
        
        while (true){
            mainMenu(userCredit,userName);
            String userCommand = userInput(sc);

            if(userCommand.equalsIgnoreCase("!exit")){
                //method status
                System.out.println("---------- See you next time! ----------");
                break;
            } else if(userCommand.equalsIgnoreCase("!rules")){
                //method rules
                rules();
                continue;
            } else if(userCommand.equalsIgnoreCase("!stats")){
                //method stats
                userStats(userName, userCredit, levelGear, botLevelGear);
                continue;
            } else if(userCommand.equalsIgnoreCase("!shop")){
                //method shop
                userCredit = shop(levelGear,userCredit,sc);
                continue;
            } else if(userCommand.equalsIgnoreCase("!play")){
                //method play
                userCredit +=playGame(levelGear, botLevelGear,sc);
                continue;
            } else {
                System.out.println("Invalid command.\nPlease check the command list on your screen.\n");
            }
        }
    }
}