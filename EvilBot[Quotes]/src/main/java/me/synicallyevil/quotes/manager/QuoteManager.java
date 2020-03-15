package me.synicallyevil.quotes.manager;

public class QuoteManager {

    public void addQuote(String message){
        /*int i = 1;

        if(!(quotes.isEmpty())){
            for (Integer key : quotes.keySet()) {
                if(key > i)
                    i = key;
            }

            i++;
        }

        quotes.put(i, message);

        saveQuotes();*/
    }

    public void removeQuote(boolean fulldelete, int number){
        /*if(fulldelete)
            quotes.remove(number);
        else
            quotes.replace(number, "*Quote removed by an Administrator.*");

        saveQuotes();*/
    }

    public String getQuote(int number){
        /*if(!(quotes.containsKey(number)))
            return "That quote doesn't exist.";

        return quotes.get(number);*/
        return "";
    }

    private void loadSavedQuotes(){
        /*File file = new File(filename);

        if(!(file.exists())){
            try{
                file.createNewFile();
            }catch(Exception ex){
                System.out.println("Couldn't create file. File: " + file);
            }

            return;
        }

        try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int l = 1;
            for(String line; (line = br.readLine()) != null; ) {
                if(line.isEmpty())
                    continue;

                quotes.put(l, line);
                //System.out.println("Added to HashMap " + l + ": " + line);
                l++;
            }
        }catch(Exception ignored){}*/

    }

    private void saveQuotes(){
        /*try{
            FileOutputStream fo = new FileOutputStream(new File(filename));
            PrintWriter pw = new PrintWriter(fo);

            for (Map.Entry<Integer, String> entry : quotes.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
                pw.println(entry.getValue());
            }

            pw.close();
            fo.close();

            reloadQuotes();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }*/
    }

    public boolean reloadQuotes(){
        /*quotes.clear();
        loadSavedQuotes();

        return quotes.size() > 0;*/
        return false;
    }
}
