import java.io.*;
import java.util.*;

public class DayFive
{
    
    ArrayList<String> wholeFile = new ArrayList<String>();
    
    ArrayList<Seed> seedList = new ArrayList<Seed>();
    
    ArrayList<ArrayList<long[]>> maps = new ArrayList<ArrayList<long[]>>();
    
    long min = -1;
    
    public void readFile(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("input5.txt"));
            String nextLine = br.readLine();
            while(nextLine != null){
                wholeFile.add(nextLine);
                nextLine = br.readLine();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void parseFile(){
        readFile();
        int parseMapIndex = -1;
        for(String line : wholeFile){
            
            if(line.split(":")[0].equalsIgnoreCase("seeds")){
                line = line.trim();
                String[] lineSplit = line.split(":")[1].trim().split(" ");
                for(int i = 0; i < lineSplit.length; i = i + 2){
                    Seed seed = new Seed();
                    seed.start = Long.parseLong(lineSplit[i]);
                    seed.end = seed.start + Long.parseLong(lineSplit[i+1]) - 1;
                    seed.mapIndex = 0;
                    seedList.add(seed);
                }
                
            }
            else if(line.split(":")[0].contains("map")){
                parseMapIndex = parseMapIndex + 1;
                maps.add(new ArrayList<long[]>());
            }else if(line.equals("")){
                
            }else{
                String[] nums = line.split(" ");
                long[] mapNums = new long[nums.length]; 
                for(int i = 0; i < nums.length; i++){
                    mapNums[i] = Long.parseLong(nums[i]);
                }
                maps.get(parseMapIndex).add(mapNums);
            }
        }
    }
    
    public void partTwo(){
        long start = System.nanoTime();
        parseFile();
        
        for(int i = 0; i < seedList.size(); i++){ 
            //FOR EACH SEED OR SEED SPLIT IN THE LIST
            Seed currSeed = seedList.get(i);
            
            
            for(int j = currSeed.mapIndex; j < maps.size(); j++){
                //FOR EACH MAP SET
                currSeed = applyMap(currSeed, j);
                
            }
            if(currSeed.start < min || min == -1){
                min = currSeed.start;
            }
        }
        long end = System.nanoTime();
        System.out.println(min);
        System.out.println( (end - start));
    }
    
    public Seed applyMap(Seed seed, int mapIndex){
        //ITERATE OVER EACH RANGE IN A MAP SET AND RETURN THE MAPPED RANGE FROM THE SEED RANGE PASSED
        ArrayList<long[]> currMap = maps.get(mapIndex);
        //a: seed range start    b: seed range end    mapStart: start of mapped location    source start: start of affected map range    source end: end of affected map range
        
        long a = seed.start; long b = seed.end; 
        
        Seed mappedRange = seed;
        
        for(int i = 0; i < currMap.size(); i++){
            long mapStart = currMap.get(i)[0]; long sourceStart = currMap.get(i)[1]; long sourceEnd = sourceStart + currMap.get(i)[2] - 1;
            //CASE: Fully Contained
            if(a >= sourceStart && b <= sourceEnd){
                mappedRange = new Seed(mapStart + a - sourceStart, mapStart + b - sourceStart, mapIndex+1);
                return mappedRange;
            }
            //CASE: Split on left side
            else if(a < sourceStart && b <= sourceEnd && b >= sourceStart){
                Seed split = new Seed(a, sourceStart - 1, mapIndex);
                mappedRange = new Seed(mapStart, mapStart + b - sourceStart, mapIndex+1);
                seedList.add(split);
                return mappedRange;
            }
            //CASE: Split on right side
            else if(a >= sourceStart && b > sourceEnd && a <= sourceEnd){
                Seed split = new Seed(sourceEnd+1, b, mapIndex);
                mappedRange = new Seed(mapStart + a - sourceStart, mapStart + sourceEnd - sourceStart, mapIndex + 1);
                seedList.add(split);
                return mappedRange;
            }
            //CASE: Split on both sides
            else if(a < sourceStart && b > sourceEnd){
                Seed split1 = new Seed(a, sourceStart-1, mapIndex);
                Seed split2 = new Seed(sourceEnd+1, b, mapIndex);
                mappedRange = new Seed(mapStart, mapStart + sourceEnd - sourceStart, mapIndex + 1);
                seedList.add(split1); seedList.add(split2);
                return mappedRange;
            }
        }
        //CASE: Seed wasn't mapped onto any of the ranges in the map set, map to default range on next map.
        mappedRange.mapIndex = mappedRange.mapIndex + 1;
        return mappedRange;
    }
}
