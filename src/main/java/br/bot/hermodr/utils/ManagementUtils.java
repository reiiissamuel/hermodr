package br.bot.hermodr.utils;

import br.bot.hermodr.Config.Messages;
import br.bot.hermodr.exceptions.ResourceNotFoundException;
import net.dv8tion.jda.api.entities.Role;
import java.util.List;

public class ManagementUtils {

    private static final String FILE_PATH = "src/main/resources/pushiments.json";

    public static boolean isNotNumber(String text){
        return !text.matches("^[0-9]*$");
    }

    public static boolean hasRoleWithName(List<Role> roles, String roleName){

        for(Role role : roles){
            if(role.getName().equals(roleName)){
                return true;
            }
        }
        return false;
    }

    public static Role getRolesFromGuildByName(List<Role> roles, String roleName) throws ResourceNotFoundException {

        for( int i = 0; i < roles.size(); i++){
            if(roles.get(i).getName().equalsIgnoreCase(roleName)){
                return roles.get(i);
            } else if(i == roles.size() - 1){
                throw new ResourceNotFoundException(Messages.ROLE_NOT_FOUND + roleName);
            }
        }
        return null;
    }


}
