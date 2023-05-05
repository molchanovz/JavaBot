package org.bot;




import org.bot.WildberriesHandler.WildberriesApiToEntity;
import org.bot.WildberriesHandler.WildberriesApiToMessage;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws JSONException, IOException {
        System.out.println(Protection.fileFBS);
        File file = new File(Protection.fileFBS);
        file.createNewFile();

    }
}

