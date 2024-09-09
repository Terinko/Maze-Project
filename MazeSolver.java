//Tyler Rinko
//03/04/2024
//CSC111
//Project 2 Maze Solver

//Importing appropriate libraries
import java.util.Scanner;
import java.io.*;
public class MazeSolver{

    //Declaring final variables to make understanding the maze easier
    private static final char CLEAR = ' ';
    private static final char FINISH = 'F';
    private static final char PATH = '^';
    private static final char VISITED = '#';
    private static final char WALL = 'x';
    private static final char START = 'S';

    //creating static variables to be used within different methods
    private static int horizontalLocation;
    private static int verticalLocation;

    private static int height;
    private static int length;

    //Method used to print maze whenever necessary
    public static void printMaze(char maze[][]){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < length; j++){
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    
    //Method that takes in a char array and tries to move the Player north
    public static boolean goNorth(char[][] maze){
        boolean success = false;
        //making sure the path ahead is a viable path
        if(verticalLocation - 1 > -1 && maze[verticalLocation - 1][horizontalLocation] == CLEAR  
        || maze[verticalLocation - 1][horizontalLocation] == FINISH){
            //marks the current path if the above requirements have been met
            maze[verticalLocation][horizontalLocation] = PATH;
            //moves the player
            verticalLocation--;
            printMaze(maze);
            //checks if the maze has been solved
            if(maze[verticalLocation - 1][horizontalLocation] == FINISH){
                success = true;
            }else{
                success = goNorth(maze);
                if(!success){
                    success = goWest(maze);
                    if(!success){
                        success = goEast(maze);
                        //backtrace steps to go south
                        if(!success){
                            maze[verticalLocation][horizontalLocation] = VISITED;
                            verticalLocation++;
                        }
                    }
                }
            }
        }
        return success;
    }

    //Method that takes in a char array and tries to move the Player west
    public static boolean goWest(char[][] maze){
        boolean success = false;
        if(horizontalLocation - 1 > -1 && maze[verticalLocation][horizontalLocation - 1] == CLEAR 
        || maze[verticalLocation][horizontalLocation - 1] == FINISH){
            maze[verticalLocation][horizontalLocation] = PATH;
            //maze[verticalLocation][horizontalLocation - 1] = PLAYER;
            horizontalLocation--;
            printMaze(maze);
            if(maze[verticalLocation - 1][horizontalLocation] == FINISH){
                success = true;
            }else{
                success = goWest(maze);
                if(!success){
                    success = goSouth(maze);
                    if(!success){
                        success = goNorth(maze);
                        if(!success){
                            maze[verticalLocation][horizontalLocation] = VISITED;
                            horizontalLocation++;
                        }
                    }
                }
            }
        }
        return success;
    }

    //Method that takes in a char array and tries to move the Player east
    public static boolean goEast(char[][] maze){
        boolean success = false;
        if(horizontalLocation + 1 < length && maze[verticalLocation][horizontalLocation + 1] == CLEAR 
        || maze[verticalLocation][horizontalLocation + 1] == FINISH){
            maze[verticalLocation][horizontalLocation] = PATH;
            //maze[verticalLocation][horizontalLocation + 1] = PLAYER;
            horizontalLocation++;
            printMaze(maze);
            if(maze[verticalLocation - 1][horizontalLocation] == FINISH){
                success = true;
            }else{
                success = goEast(maze);
                if(!success){
                    success = goNorth(maze);
                    if(!success){
                        success = goSouth(maze);
                        if(!success){
                            maze[verticalLocation][horizontalLocation] = VISITED;
                            horizontalLocation--;
                        }
                    }
                }
            }
        }
        return success;
    }

    //Method that takes in a char array and tries to move the Player south
    public static boolean goSouth(char[][] maze){
        boolean success = false;
        if(verticalLocation + 1 < height && maze[verticalLocation + 1][horizontalLocation] == CLEAR
        || maze[verticalLocation + 1][horizontalLocation] == FINISH){
            //mark location as part of the path
            maze[verticalLocation][horizontalLocation] = PATH;
            verticalLocation++;
            printMaze(maze);
            //check if the square above the "player" is the finish and returns true if true
            if(maze[verticalLocation - 1][horizontalLocation] == FINISH){
                success = true;
            }else{
                success = goSouth(maze);
                if(!success){
                    success = goEast(maze);
                    if(!success){
                        success = goWest(maze);
                        if(!success){
                            maze[verticalLocation][horizontalLocation] = VISITED;
                            verticalLocation--;
                        }
                    }
                }
            }
        }
        return success;
    }

    public static void main(String[] args){
        //defining variables
        String inputLine;
        Scanner fileInput;
        //reading specific file
        File inFile = new File("maze.txt");

        char[][] components;
        //try in case the file does not exist or is in the wrong location
        try{
            //initializing scanner object
            fileInput = new Scanner(inFile);
            //reading in necessary variables
            length = fileInput.nextInt(); //20
            height = fileInput.nextInt(); //7
            int finishX = fileInput.nextInt();
            int finishY = fileInput.nextInt();
            horizontalLocation = fileInput.nextInt();
            verticalLocation = fileInput.nextInt();
            //initializing the maze array
            components = new char[height][length];
            int i = 0;
            //filling 2d array with characters
            while(fileInput.hasNextLine()){
                inputLine = fileInput.nextLine();
                for(int j = 0; j < inputLine.length(); j++){
                    components[i - 1][j] = inputLine.charAt(j);
                }
                i++;
                //locating start and end points within the array
                components[verticalLocation][horizontalLocation] = START;
                components[finishY][finishX] = FINISH;
                //printing array that was just created char by char
                for(int j = 0; j < inputLine.length(); j++){
                    System.out.print(components[i - 2][j]);
                }
                System.out.println();
            } // end while
            //close scanner
            fileInput.close();
            //PrintMaze(components);

            //if there is a solution
            if(goNorth(components)){
                System.out.println();
                System.out.println("Maze was completed");
                System.out.println();
                printMaze(components);
            //if there is not a solution (i.e goNorth returns false)
            }else{
                System.out.println("Maze doesn't have a solution");
            }
        } // end try
        catch (FileNotFoundException e){
            System.out.println(e);
            System.exit(1); // IO error; exit program
        } // end catch
        //System.out.println("end of program");

    }
}