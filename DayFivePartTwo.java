import java.io.*;
import java.util.*;


public class DayFivePartTwo
{
    String[] seeds;
    
    ArrayList<long[]> seedRanges = new ArrayList<long[]>();
    ArrayList<long[]> rangeDestinations = new ArrayList<long[]>();
    ArrayList<ArrayList<long[]>> maps = new ArrayList<ArrayList<long[]>>();
    
    public void partTwo(){
        
        parseFile();
        convertSeedToRange();
        
        for(int i = 0; i < seedRanges.size(); i++){
            long[] location = findLocationRange(seedRanges.get(i));
            rangeDestinations.add(location);
        }
        
        long min = rangeDestinations.get(0)[0];
        for(int i = 0; i < rangeDestinations.size(); i++){
            if(rangeDestinations.get(i)[0] < min){
                min = rangeDestinations.get(i)[0];
            }
        }
        System.out.println(min);
    }
    
    public long[] findLocationRange(long[] seedRange){
        long[] currRange = seedRange;
        for(int i = 0; i < maps.size(); i++){
            currRange = matchAndClipMap(currRange, i);
        }
        
        return currRange;
    }
    
    public long[] matchAndClipMap(long[] currRange, int map){
        long[] mappedRange = new long[2];
            
        for(int i = 0; i < maps.get(map).size(); i++){
            long range = maps.get(map).get(i)[2];
            long mapStart = maps.get(map).get(i)[0]; long destB = mapStart + range -1;
            long sourceStart = maps.get(map).get(i)[1]; 
            long a = currRange[0]; long b = currRange[1];
            
            long sourceEnd = sourceStart + range-1;
            System.out.println("--------------------------------------------------------------------------------------");
            System.out.println("Current Range: "+a +", "+b);
            System.out.println("Current sourceStart: "+sourceStart + " Current sourceEnd: " + sourceEnd);
            System.out.println("Range: " + range);
            System.out.println("Destination start: " +mapStart + " Max Destination: " +destB);
            
            // CASE: FULLY CONTAINED
            
            if( a >= sourceStart && b <= sourceEnd){
                System.out.println("Fully Contained");
                mappedRange[0] = mapStart + (a - sourceStart);
                mappedRange[1] = mapStart + (b - sourceStart);
                
                return mappedRange;
            }
            //CASE SPLIT LEFT
            else if(a < sourceStart && b <= sourceEnd && b >= sourceStart){
                System.out.println("Split left");
                long splitA = a;
                long splitB = sourceStart - 1;
                long[] split = {splitA, splitB};
                mappedRange[0] = mapStart;
                mappedRange[1] = mapStart + b - sourceStart;    
                seedRanges.add(split);
                return mappedRange;
            }
            //CASE SPLIT RIGHT
            else if(a >= sourceStart && b > sourceEnd && a <= sourceEnd){
                System.out.println("Split Right");
                long splitA = sourceEnd+1;
                long splitB = b;
                long[] split = {splitA, splitB};
                mappedRange[0] = mapStart + a - sourceStart;
                mappedRange[1] = mapStart + sourceEnd - sourceStart;            
                seedRanges.add(split);
                return mappedRange;
            }
            //CASE SPLIT BOTH
            else if(a < sourceStart && b > sourceEnd){
                System.out.println("Split Both");
                long splitA = a;
                long splitB = sourceStart-1;
                long[] split1 = {splitA, splitB};
                mappedRange[0] = mapStart;
                mappedRange[1] = mapStart + sourceEnd - sourceStart;
                splitA = sourceEnd + 1;
                splitB = b;
                long[] split2 = {splitA, splitB};
                seedRanges.add(split1);
                seedRanges.add(split2);
            }
        }
        return currRange;
    }
    
    public void convertSeedToRange(){
        for(int i = 0; i < seeds.length; i = i+2){
            long seedStart = Long.parseLong(seeds[i]);
            long seedEnd = Long.parseLong(seeds[i+1]);
            long[] seedRange = {seedStart, seedStart + seedEnd - 1};
            seedRanges.add(seedRange);
        }
    }
    
    
    public void parseFile(){
        ReadFile fr = new ReadFile();
        ArrayList<String> wholeFile = fr.readFile();
        int mapIndex = -1;
        for(String line : wholeFile){
            String tagparts[] = line.split(":")[0].split(" ");
            String tag = tagparts[tagparts.length - 1];
            if(!line.equalsIgnoreCase("")){
                switch(tag){
                    case "seeds":
                        seeds = line.split(":")[1].split(" ");
                        seeds = Arrays.copyOfRange(seeds, 1, seeds.length);
                        break;
                    case "map":
                        maps.add(new ArrayList<long[]>());
                        mapIndex++;
                        break;
                    default:
                        String[] strMapNums = line.split(" ");
                        long[] mapNums = new long[strMapNums.length];
                        for(int i = 0; i < strMapNums.length; i++){
                            if(!strMapNums[i].equalsIgnoreCase("")){
                                mapNums[i] = Long.parseLong(strMapNums[i].trim());
                            }
                        }
                        maps.get(mapIndex).add(mapNums);
                        break;
                }
            }
        }
    }
}
