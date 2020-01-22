package se.iths.jelleryd.webshop.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// @RestController
public class RestController {

  // @Autowired
  // MooGameService mgs;
  //
  // @GetMapping("/moo/{guess}")
  // public ResponseEntity<List<GuessAndAnswer>> makeGuess(@PathVariable("guess") String guess){
  // List<GuessAndAnswer> list = mgs.makeGuess(guess);
  // if (list != null ) {
  // return ResponseEntity.accepted().body(list);
  // } else {
  // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
  // }
  // }
  //
  // @GetMapping("/moo/login/{name}")
  // public ResponseEntity<String> login (@PathVariable("name")String player) {
  // if (mgs.login(player)) {
  // return ResponseEntity.accepted().body("Logged in, welcome!");
  // } else {
  // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No such player");
  // }
  // }
  //
  // @GetMapping("/moo/topten")
  // public List<PlayerAndAverage> topten() {
  // return mgs.topTen();
  // }

}
