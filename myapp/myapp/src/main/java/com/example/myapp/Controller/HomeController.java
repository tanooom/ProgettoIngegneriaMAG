package com.example.myapp.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.myapp.Model.Partita;
import com.example.myapp.Model.Utente;
import com.example.myapp.Service.UserService;
import com.example.myapp.Service.MapDBService;
import com.example.myapp.Service.PartitaService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private PartitaService partitaService;

    @Autowired
    private MapDBService mapDBService;

    @GetMapping("/home")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("nome", user.getNome());
            model.addAttribute("cognome", user.getCognome());
        }
        return "home";
    }

    @GetMapping("/profilo")
    public String profilo(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Utente user = userService.getUser(username);
        if (user != null) {
            model.addAttribute("user", user); 
        }
        return "profilo";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        SecurityContextHolder.clearContext();
        return "redirect:/login?logout";
    }

    @GetMapping("/visualizzaStoria")
    public String visualizzaStoria() {
        return "visualizzaStoria";
    }

    @GetMapping("/giocaStoria")
    public String giocaStoria() {
        return "giocaStoria";
    }

    @GetMapping("/leMiePartite")
    public String leMiePartite(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        List<Partita> partite = partitaService.getPartiteByUsername(username);
        model.addAttribute("partite", partite);
        model.addAttribute("hasMatches", !partite.isEmpty());

        return "leMiePartite";
    }

    @GetMapping("/getPartiteByUsername")
    @ResponseBody
    public List<Partita> getPartiteByUsername(@RequestParam String username) {
        return partitaService.getPartiteByUsername(username);
    }
    
    @GetMapping("/getAllPartite")
    @ResponseBody
    public List<Partita> getAllPartite() {
        return mapDBService.getListAllPartite();
    }

    @DeleteMapping("/deletePartita")
    public ResponseEntity<String> deleteMatch(@RequestParam int id) {
        try {
            mapDBService.removeMatch(id);
            mapDBService.removeInventory(id);
            return ResponseEntity.ok("Partita eliminata con successo!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore interno del server: " + e.getMessage());
        }
    }

}