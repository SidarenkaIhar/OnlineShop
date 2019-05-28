package com.epam.training.onlineshop;

import com.epam.training.onlineshop.dao.DAOFactory;
import com.epam.training.onlineshop.dao.UserDAO;
import com.epam.training.onlineshop.entity.user.User;

import java.util.List;

/**
 * Online shop.
 *
 * @author Ihar Sidarenka
 * @version 0.1 07 Apr 2019
 */
public class OnlineShop {
    public static void main(String[] args) {
        //  TODO  временные комментарии для теста

        // создать требуемый генератор DAO
        DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        // Создать DAO пользователя
        UserDAO userDAO = mysqlFactory.getUserDAO();
        // создать пользователя клиента
        User user = new User("admin", "123456a", "admin@admin.test");
        //  добавить нового пользователя в бд
        System.out.println("addUser " + user.getName() + " - \t" + userDAO.addNew(user));
        //  поиск пользователей в бд
        List<User> users = userDAO.findAll();
        for (User us : users) {
            //  вывод информации о пользователе
            System.out.println(us);
            //  установить ид номер пользователя
            user.setId(us.getId());
        }
        //  добавить пользователя в черный список
        System.out.println("addUserToBlacklist " + user.getName() + " - \t" + userDAO.addUserToBlacklist(user));
        //  изменить информацию о пользователе
        user.setEmail("test@test.test");
        //  изменить информацию о пользователе в бд
        System.out.println("editUser " + user.getName() + " - \t" + userDAO.update(user));
        //  удалить пользователя из бд
        System.out.println("deleteUser " + user.getName() + " - \t" + userDAO.delete(user));
    }
}
