package com.example.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    final private String TMDB_POPULAR_RESULTS = "{\"page\":1,\"total_results\":10000,\"total_pages\":500,\"results\":[{\"popularity\":462.659,\"vote_count\":2853,\"video\":false,\"poster_path\":\"\\/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg\",\"id\":419704,\"adult\":false,\"backdrop_path\":\"\\/5BwqwxMEjeFtdknRV792Svo0K1v.jpg\",\"original_language\":\"en\",\"original_title\":\"Ad Astra\",\"genre_ids\":[18,878],\"title\":\"Ad Astra\",\"vote_average\":5.9,\"overview\":\"The near future, a time when both hope and hardships drive humanity to look to the stars and beyond. While a mysterious phenomenon menaces to destroy life on planet Earth, astronaut Roy McBride undertakes a mission across the immensity of space and its many perils to uncover the truth about a lost expedition that decades before boldly faced emptiness and silence in search of the unknown.\",\"release_date\":\"2019-09-17\"},{\"popularity\":247.853,\"vote_count\":1349,\"video\":false,\"poster_path\":\"\\/8WUVHemHFH2ZIP6NWkwlHWsyrEL.jpg\",\"id\":338762,\"adult\":false,\"backdrop_path\":\"\\/ocUrMYbdjknu2TwzMHKT9PBBQRw.jpg\",\"original_language\":\"en\",\"original_title\":\"Bloodshot\",\"genre_ids\":[28,878],\"title\":\"Bloodshot\",\"vote_average\":7.2,\"overview\":\"After he and his wife are murdered, marine Ray Garrison is resurrected by a team of scientists. Enhanced with nanotechnology, he becomes a superhuman, biotech killing machine—'Bloodshot'. As Ray first trains with fellow super-soldiers, he cannot recall anything from his former life. But when his memories flood back and he remembers the man that killed both him and his wife, he breaks out of the facility to get revenge, only to discover that there's more to the conspiracy than he thought.\",\"release_date\":\"2020-03-05\"},{\"popularity\":235.696,\"vote_count\":2530,\"video\":false,\"poster_path\":\"\\/y95lQLnuNKdPAzw9F9Ab8kJ80c3.jpg\",\"id\":38700,\"adult\":false,\"backdrop_path\":\"\\/upUy2QhMZEmtypPW3PdieKLAHxh.jpg\",\"original_language\":\"en\",\"original_title\":\"Bad Boys for Life\",\"genre_ids\":[28,80,53],\"title\":\"Bad Boys for Life\",\"vote_average\":7.1,\"overview\":\"Marcus and Mike are forced to confront new threats, career changes, and midlife crises as they join the newly created elite team AMMO of the Miami police department to take down the ruthless Armando Armas, the vicious leader of a Miami drug cartel.\",\"release_date\":\"2020-01-15\"},{\"popularity\":223.823,\"vote_count\":13611,\"video\":false,\"poster_path\":\"\\/D6e8RJf2qUstnfkTslTXNTUAlT.jpg\",\"id\":102899,\"adult\":false,\"backdrop_path\":\"\\/kvXLZqY0Ngl1XSw7EaMQO0C1CCj.jpg\",\"original_language\":\"en\",\"original_title\":\"Ant-Man\",\"genre_ids\":[28,12,878],\"title\":\"Ant-Man\",\"vote_average\":7.1,\"overview\":\"Armed with the astonishing ability to shrink in scale but increase in strength, master thief Scott Lang must embrace his inner-hero and help his mentor, Doctor Hank Pym, protect the secret behind his spectacular Ant-Man suit from a new generation of towering threats. Against seemingly insurmountable obstacles, Pym and Lang must plan and pull off a heist that will save the world.\",\"release_date\":\"2015-07-14\"},{\"popularity\":207.361,\"vote_count\":3542,\"video\":false,\"poster_path\":\"\\/k1bhUW7XM5X0yD3iewAEvloFBEo.jpg\",\"id\":76285,\"adult\":false,\"backdrop_path\":\"\\/3NK02PLJSs01SY1hsXUAcqbG3WP.jpg\",\"original_language\":\"en\",\"original_title\":\"Percy Jackson: Sea of Monsters\",\"genre_ids\":[12,14,10751],\"title\":\"Percy Jackson: Sea of Monsters\",\"vote_average\":5.9,\"overview\":\"In their quest to confront the ultimate evil, Percy and his friends battle swarms of mythical creatures to find the mythical Golden Fleece and to stop an ancient evil from rising.\",\"release_date\":\"2013-08-07\"},{\"popularity\":195.31,\"vote_count\":2639,\"video\":false,\"poster_path\":\"\\/h4VB6m0RwcicVEZvzftYZyKXs6K.jpg\",\"id\":495764,\"adult\":false,\"backdrop_path\":\"\\/jiqD14fg7UTZOT6qgvzTmfRYpWI.jpg\",\"original_language\":\"en\",\"original_title\":\"Birds of Prey (and the Fantabulous Emancipation of One Harley Quinn)\",\"genre_ids\":[28,35,80],\"title\":\"Birds of Prey (and the Fantabulous Emancipation of One Harley Quinn)\",\"vote_average\":7.1,\"overview\":\"Harley Quinn joins forces with a singer, an assassin and a police detective to help a young girl who had a hit placed on her after she stole a rare diamond from a crime lord.\",\"release_date\":\"2020-02-05\"},{\"popularity\":169.05,\"vote_count\":3714,\"video\":false,\"poster_path\":\"\\/8czarUCdvqPnulkLX8mdXyrLk2D.jpg\",\"id\":1571,\"adult\":false,\"backdrop_path\":\"\\/kKfkIki3efmqODxSSvDMIhykY9o.jpg\",\"original_language\":\"en\",\"original_title\":\"Live Free or Die Hard\",\"genre_ids\":[28,53],\"title\":\"Live Free or Die Hard\",\"vote_average\":6.5,\"overview\":\"John McClane is back and badder than ever, and this time he's working for Homeland Security. He calls on the services of a young hacker in his bid to stop a ring of Internet terrorists intent on taking control of America's computer infrastructure.\",\"release_date\":\"2007-06-20\"},{\"popularity\":162.842,\"vote_count\":119,\"video\":false,\"poster_path\":\"\\/uGMIxhhMvQEE0mNlATXpEAG4mgl.jpg\",\"id\":595985,\"adult\":false,\"backdrop_path\":\"\\/ob8w7gQNV4uL43fCGbD6y89rpDn.jpg\",\"original_language\":\"fr\",\"original_title\":\"Cold Blood Legacy - La mémoire du sang\",\"genre_ids\":[28,53],\"title\":\"Cold Blood\",\"vote_average\":5.1,\"overview\":\"A legendary but retired hit man lives in peace and isolation in the barren North American wilderness. When he rescues a woman from a snowmobiling accident, he soon discovers that she's harboring a secret that forces him to return to his lethal ways.\",\"release_date\":\"2019-05-15\"},{\"popularity\":154.759,\"vote_count\":584,\"video\":false,\"poster_path\":\"\\/6vaqTxkRnoGwjeLqyZbVuVR7Snv.jpg\",\"id\":443791,\"adult\":false,\"backdrop_path\":\"\\/fYPiQewg7ogbzro2XcCTACSB2KC.jpg\",\"original_language\":\"en\",\"original_title\":\"Underwater\",\"genre_ids\":[28,27,878,53],\"title\":\"Underwater\",\"vote_average\":6.5,\"overview\":\"After an earthquake destroys their underwater station, six researchers must navigate two miles along the dangerous, unknown depths of the ocean floor to make it to safety in a race against time.\",\"release_date\":\"2020-01-08\"},{\"popularity\":144.803,\"vote_count\":1924,\"video\":false,\"poster_path\":\"\\/8ZX18L5m6rH5viSYpRnTSbb9eXh.jpg\",\"id\":619264,\"adult\":false,\"backdrop_path\":\"\\/3tkDMNfM2YuIAJlvGO6rfIzAnfG.jpg\",\"original_language\":\"es\",\"original_title\":\"El hoyo\",\"genre_ids\":[18,878],\"title\":\"The Platform\",\"vote_average\":7.2,\"overview\":\"A mysterious place, an indescribable prison, a deep hole. An unknown number of levels. Two inmates living on each level. A descending platform containing food for all of them. An inhuman fight for survival, but also an opportunity for solidarity…\",\"release_date\":\"2019-11-08\"},{\"popularity\":144.135,\"vote_count\":2974,\"video\":false,\"poster_path\":\"\\/bB42KDdfWkOvmzmYkmK58ZlCa9P.jpg\",\"id\":512200,\"adult\":false,\"backdrop_path\":\"\\/oLma4sWjqlXVr0E3jpaXQCytuG9.jpg\",\"original_language\":\"en\",\"original_title\":\"Jumanji: The Next Level\",\"genre_ids\":[12,35,14],\"title\":\"Jumanji: The Next Level\",\"vote_average\":6.8,\"overview\":\"As the gang return to Jumanji to rescue one of their own, they discover that nothing is as they expect. The players will have to brave parts unknown and unexplored in order to escape the world’s most dangerous game.\",\"release_date\":\"2019-12-04\"},{\"popularity\":143.316,\"vote_count\":5687,\"video\":false,\"poster_path\":\"\\/sOpCY4by0bkEu9LbVXVCOpWWKzF.jpg\",\"id\":24021,\"adult\":false,\"backdrop_path\":\"\\/oguoQWtQJcqIkmmUNbe3CmLx6AP.jpg\",\"original_language\":\"en\",\"original_title\":\"The Twilight Saga: Eclipse\",\"genre_ids\":[12,18,14,10749],\"title\":\"The Twilight Saga: Eclipse\",\"vote_average\":6.1,\"overview\":\"Bella once again finds herself surrounded by danger as Seattle is ravaged by a string of mysterious killings and a malicious vampire continues her quest for revenge. In the midst of it all, she is forced to choose between her love for Edward and her friendship with Jacob, knowing that her decision has the potential to ignite the ageless struggle between vampire and werewolf. With her graduation quickly approaching, Bella is confronted with the most important decision of her life.\",\"release_date\":\"2010-06-23\"},{\"popularity\":139.029,\"vote_count\":2066,\"video\":false,\"poster_path\":\"\\/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg\",\"id\":454626,\"adult\":false,\"backdrop_path\":\"\\/stmYfCUGd8Iy6kAMBr6AmWqx8Bq.jpg\",\"original_language\":\"en\",\"original_title\":\"Sonic the Hedgehog\",\"genre_ids\":[28,35,878,10751],\"title\":\"Sonic the Hedgehog\",\"vote_average\":7.4,\"overview\":\"Based on the global blockbuster videogame franchise from Sega, Sonic the Hedgehog tells the story of the world’s speediest hedgehog as he embraces his new home on Earth. In this live-action adventure comedy, Sonic and his new best friend team up to defend the planet from the evil genius Dr. Robotnik and his plans for world domination.\",\"release_date\":\"2020-02-12\"},{\"popularity\":136.803,\"vote_count\":3800,\"video\":false,\"poster_path\":\"\\/db32LaOibwEliAmSL2jjDF6oDdj.jpg\",\"id\":181812,\"adult\":false,\"backdrop_path\":\"\\/jOzrELAzFxtMx2I4uDGHOotdfsS.jpg\",\"original_language\":\"en\",\"original_title\":\"Star Wars: The Rise of Skywalker\",\"genre_ids\":[28,12,878],\"title\":\"Star Wars: The Rise of Skywalker\",\"vote_average\":6.5,\"overview\":\"The surviving Resistance faces the First Order once again as the journey of Rey, Finn and Poe Dameron continues. With the power and knowledge of generations behind them, the final battle begins.\",\"release_date\":\"2019-12-18\"},{\"popularity\":131.453,\"vote_count\":956,\"video\":false,\"poster_path\":\"\\/f4aul3FyD3jv3v4bul1IrkWZvzq.jpg\",\"id\":508439,\"adult\":false,\"backdrop_path\":\"\\/xFxk4vnirOtUxpOEWgA1MCRfy6J.jpg\",\"original_language\":\"en\",\"original_title\":\"Onward\",\"genre_ids\":[12,16,35,14,10751],\"title\":\"Onward\",\"vote_average\":8,\"overview\":\"In a suburban fantasy world, two teenage elf brothers embark on an extraordinary quest to discover if there is still a little magic left out there.\",\"release_date\":\"2020-02-29\"},{\"popularity\":129.746,\"vote_count\":148,\"video\":false,\"poster_path\":\"\\/hd5YQjsIPuLmZuvWfdv36apz8tE.jpg\",\"id\":556678,\"adult\":false,\"backdrop_path\":\"\\/5GbkL9DDRzq3A21nR7Gkv6cFGjq.jpg\",\"original_language\":\"en\",\"original_title\":\"Emma.\",\"genre_ids\":[35,18,10749],\"title\":\"Emma.\",\"vote_average\":7.1,\"overview\":\"In 1800s England, a well-meaning but selfish young woman meddles in the love lives of her friends.\",\"release_date\":\"2020-02-13\"},{\"popularity\":120.441,\"vote_count\":845,\"video\":false,\"poster_path\":\"\\/gAnmNjw1TCTzS702mj1OwwmXBpM.jpg\",\"id\":13761,\"adult\":false,\"backdrop_path\":\"\\/fzAnRGmaKJtm8sZuZ8a1nwSNTd3.jpg\",\"original_language\":\"en\",\"original_title\":\"Pocahontas II: Journey to a New World\",\"genre_ids\":[12,16,10749,10751],\"title\":\"Pocahontas II: Journey to a New World\",\"vote_average\":5.3,\"overview\":\"When news of John Smith's death reaches America, Pocahontas is devastated. She sets off to London with John Rolfe, to meet with the King of England on a diplomatic mission: to create peace and respect between the two great lands. However, Governor Ratcliffe is still around; he wants to return to Jamestown and take over. He will stop at nothing to discredit the young princess.\",\"release_date\":\"1998-08-04\"},{\"popularity\":115.841,\"vote_count\":2896,\"video\":false,\"poster_path\":\"\\/yo6FSOs3uxsT8gp3FVd7c7RCLFs.jpg\",\"id\":1996,\"adult\":false,\"backdrop_path\":\"\\/iv6BhAYeJ440OsCGiFKbyBqILVZ.jpg\",\"original_language\":\"en\",\"original_title\":\"Lara Croft: Tomb Raider - The Cradle of Life\",\"genre_ids\":[28,12,14,53],\"title\":\"Lara Croft: Tomb Raider - The Cradle of Life\",\"vote_average\":5.7,\"overview\":\"Lara Croft ventures to an underwater temple in search of the mythological Pandora's Box but, after securing it, it is promptly stolen by the villainous leader of a Chinese crime syndicate. Lara must recover the box before the syndicate's evil mastermind uses it to construct a weapon of catastrophic capabilities.\",\"release_date\":\"2003-07-21\"},{\"popularity\":114.177,\"vote_count\":1249,\"video\":false,\"poster_path\":\"\\/5EufsDwXdY2CVttYOk2WtYhgKpa.jpg\",\"id\":570670,\"adult\":false,\"backdrop_path\":\"\\/uZMZyvarQuXLRqf3xdpdMqzdtjb.jpg\",\"original_language\":\"en\",\"original_title\":\"The Invisible Man\",\"genre_ids\":[27,878,53],\"title\":\"The Invisible Man\",\"vote_average\":7.2,\"overview\":\"When Cecilia's abusive ex takes his own life and leaves her his fortune, she suspects his death was a hoax. As a series of coincidences turn lethal, Cecilia works to prove that she is being hunted by someone nobody can see.\",\"release_date\":\"2020-02-26\"},{\"popularity\":112.879,\"vote_count\":946,\"video\":false,\"poster_path\":\"\\/cKDGrVItQTdDn6LqcICz91AOxs3.jpg\",\"id\":309886,\"adult\":false,\"backdrop_path\":\"\\/nujQTIz5hc1fWnlH23s0sY0NCuq.jpg\",\"original_language\":\"en\",\"original_title\":\"Blood Father\",\"genre_ids\":[28,18,53],\"title\":\"Blood Father\",\"vote_average\":6.1,\"overview\":\"An ex-con reunites with his estranged wayward 16-year old daughter to protect her from drug dealers who are trying to kill her.\",\"release_date\":\"2016-08-11\"}]}";
    final private String TMDB_HIGHEST_RATED_RESULTS = "{\"page\":1,\"total_results\":7101,\"total_pages\":356,\"results\":[{\"popularity\":28.448,\"vote_count\":2230,\"video\":false,\"poster_path\":\"\\/2CAL2433ZeIihfX1Hb2139CX0pW.jpg\",\"id\":19404,\"adult\":false,\"backdrop_path\":\"\\/mMaxGuQKuH4WUHBwUNhJOetDYE9.jpg\",\"original_language\":\"hi\",\"original_title\":\"दिलवाले दुल्हनिया ले जायेंगे\",\"genre_ids\":[35,18,10749],\"title\":\"Dilwale Dulhania Le Jayenge\",\"vote_average\":8.8,\"overview\":\"Raj is a rich, carefree, happy-go-lucky second generation NRI. Simran is the daughter of Chaudhary Baldev Singh, who in spite of being an NRI is very strict about adherence to Indian values. Simran has left for India to be married to her childhood fiancé. Raj leaves for India with a mission at his hands, to claim his lady love under the noses of her whole family. Thus begins a saga.\",\"release_date\":\"1995-10-20\"},{\"popularity\":48.086,\"id\":278,\"video\":false,\"vote_count\":15522,\"vote_average\":8.7,\"title\":\"The Shawshank Redemption\",\"release_date\":\"1994-09-23\",\"original_language\":\"en\",\"original_title\":\"The Shawshank Redemption\",\"genre_ids\":[18,80],\"backdrop_path\":\"\\/9Xw0I5RV2ZqNLpul6lXKoviYg55.jpg\",\"adult\":false,\"overview\":\"Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.\",\"poster_path\":\"\\/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg\"},{\"popularity\":46.542,\"vote_count\":11768,\"video\":false,\"poster_path\":\"\\/iVZ3JAcAjmguGPnRNfWFOtLHOuY.jpg\",\"id\":238,\"adult\":false,\"backdrop_path\":\"\\/ejdD20cdHNFAYAN2DlqPToXKyzx.jpg\",\"original_language\":\"en\",\"original_title\":\"The Godfather\",\"genre_ids\":[80,18],\"title\":\"The Godfather\",\"vote_average\":8.7,\"overview\":\"Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.\",\"release_date\":\"1972-03-14\"},{\"popularity\":32.452,\"id\":424,\"video\":false,\"vote_count\":9366,\"vote_average\":8.6,\"title\":\"Schindler's List\",\"release_date\":\"1993-11-30\",\"original_language\":\"en\",\"original_title\":\"Schindler's List\",\"genre_ids\":[18,36,10752],\"backdrop_path\":\"\\/cTNYRUTXkBgPH3wP3kmPUB5U6dA.jpg\",\"adult\":false,\"overview\":\"The true story of how businessman Oskar Schindler saved over a thousand Jewish lives from the Nazis while they worked as slaves in his factory during World War II.\",\"poster_path\":\"\\/c8Ass7acuOe4za6DhSattE359gr.jpg\"},{\"popularity\":33.181,\"vote_count\":6917,\"video\":false,\"poster_path\":\"\\/bVq65huQ8vHDd1a4Z37QtuyEvpA.jpg\",\"id\":240,\"adult\":false,\"backdrop_path\":\"\\/gLbBRyS7MBrmVUNce91Hmx9vzqI.jpg\",\"original_language\":\"en\",\"original_title\":\"The Godfather: Part II\",\"genre_ids\":[80,18],\"title\":\"The Godfather: Part II\",\"vote_average\":8.5,\"overview\":\"In the continuing saga of the Corleone crime family, a young Vito Corleone grows up in Sicily and in 1910s New York. In the 1950s, Michael Corleone attempts to expand the family business into Las Vegas, Hollywood and Cuba.\",\"release_date\":\"1974-12-20\"},{\"popularity\":35.255,\"vote_count\":5225,\"video\":false,\"poster_path\":\"\\/xq1Ugd62d23K2knRUx6xxuALTZB.jpg\",\"id\":372058,\"adult\":false,\"backdrop_path\":\"\\/7OMAfDJikBxItZBIug0NJig5DHD.jpg\",\"original_language\":\"ja\",\"original_title\":\"君の名は。\",\"genre_ids\":[16,18,10749],\"title\":\"Your Name.\",\"vote_average\":8.5,\"overview\":\"High schoolers Mitsuha and Taki are complete strangers living separate lives. But one night, they suddenly switch places. Mitsuha wakes up in Taki’s body, and he in hers. This bizarre occurrence continues to happen randomly, and the two must adjust their lives around each other.\",\"release_date\":\"2016-08-26\"},{\"popularity\":92.215,\"vote_count\":6046,\"video\":false,\"poster_path\":\"\\/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg\",\"id\":496243,\"adult\":false,\"backdrop_path\":\"\\/TU9NIjwzjoKPwQHoHshkFcQUCG.jpg\",\"original_language\":\"ko\",\"original_title\":\"기생충\",\"genre_ids\":[35,18,53],\"title\":\"Parasite\",\"vote_average\":8.5,\"overview\":\"All unemployed, Ki-taek's family takes peculiar interest in the wealthy and glamorous Parks for their livelihood until they get entangled in an unexpected incident.\",\"release_date\":\"2019-05-30\"},{\"popularity\":8.876,\"vote_count\":207,\"video\":false,\"poster_path\":\"\\/zGGWYpiKNwjpKxelPxOMqJnUgDs.jpg\",\"id\":42269,\"adult\":false,\"backdrop_path\":\"\\/bh6yfB6I1N1WuMiX6K5HxGVZnja.jpg\",\"original_language\":\"it\",\"original_title\":\"C'eravamo tanto amati\",\"genre_ids\":[35,18],\"title\":\"We All Loved Each Other So Much\",\"vote_average\":8.5,\"overview\":\"Gianni, Nicola and Antonio become close friends in 1944 while fighting the Nazis. After the end of the war, full of illusions, they settle down. The movie is a the story of the life of these three idealists and how they deal with the inevitable disillusionments of life.\",\"release_date\":\"1974-12-21\"},{\"popularity\":40.37,\"vote_count\":9009,\"video\":false,\"poster_path\":\"\\/oRvMaJOmapypFUcQqpgHMZA6qL9.jpg\",\"id\":129,\"adult\":false,\"backdrop_path\":\"\\/eyaygChVcUyv2gOVAJl8OUwoYm2.jpg\",\"original_language\":\"ja\",\"original_title\":\"千と千尋の神隠し\",\"genre_ids\":[16,14,10751],\"title\":\"Spirited Away\",\"vote_average\":8.5,\"overview\":\"A young girl, Chihiro, becomes trapped in a strange new world of spirits. When her parents undergo a mysterious transformation, she must call upon the courage she never knew she had to free her family.\",\"release_date\":\"2001-07-20\"},{\"popularity\":17.123,\"vote_count\":218,\"video\":false,\"poster_path\":\"\\/zw77BFPGJ73Lig8GwRzYj1XHq53.jpg\",\"id\":620683,\"adult\":false,\"backdrop_path\":\"\\/4fvnZ7Oazi6JR8m2E7T6zhLXqJY.jpg\",\"original_language\":\"pt\",\"original_title\":\"Minha Mãe é Uma Peça 3: O Filme\",\"genre_ids\":[35],\"title\":\"My Mom is a Character 3: The Film\",\"vote_average\":8.5,\"overview\":\"Dona Herminia will have to rediscover and reinvent herself because her children are forming new families. This supermom will have to deal with a new life scenario: Marcelina is pregnant and Juliano is getting married.\",\"release_date\":\"2019-12-26\"},{\"popularity\":21.75,\"id\":497,\"video\":false,\"vote_count\":9740,\"vote_average\":8.5,\"title\":\"The Green Mile\",\"release_date\":\"1999-12-10\",\"original_language\":\"en\",\"original_title\":\"The Green Mile\",\"genre_ids\":[14,18,80],\"backdrop_path\":\"\\/Rlt20sEbOQKPVjia7lUilFm49W.jpg\",\"adult\":false,\"overview\":\"A supernatural tale set on death row in a Southern prison, where gentle giant John Coffey possesses the mysterious power to heal people's ailments. When the cell block's head guard, Paul Edgecomb, recognizes Coffey's miraculous gift, he tries desperately to help stave off the condemned man's execution.\",\"poster_path\":\"\\/sOHqdY1RnSn6kcfAHKu28jvTebE.jpg\"},{\"popularity\":34.307,\"vote_count\":18003,\"video\":false,\"poster_path\":\"\\/d5iIlFn5s0ImszYzBPb8JPIfbXD.jpg\",\"id\":680,\"adult\":false,\"backdrop_path\":\"\\/suaEOtk1N1sgg2MTM7oZd2cfVp3.jpg\",\"original_language\":\"en\",\"original_title\":\"Pulp Fiction\",\"genre_ids\":[80,53],\"title\":\"Pulp Fiction\",\"vote_average\":8.5,\"overview\":\"A burger-loving hit man, his philosophical partner, a drug-addled gangster's moll and a washed-up boxer converge in this sprawling, comedic crime caper. Their adventures unfurl in three stories that ingeniously trip back and forth in time.\",\"release_date\":\"1994-09-10\"},{\"popularity\":26.06,\"vote_count\":8477,\"video\":false,\"poster_path\":\"\\/6tEJnof1DKWPnl5lzkjf0FVv7oB.jpg\",\"id\":637,\"adult\":false,\"backdrop_path\":\"\\/bORe0eI72D874TMawOOFvqWS6Xe.jpg\",\"original_language\":\"it\",\"original_title\":\"La vita è bella\",\"genre_ids\":[35,18],\"title\":\"Life Is Beautiful\",\"vote_average\":8.5,\"overview\":\"A touching story of an Italian book seller of Jewish ancestry who lives in his own little fairy tale. His creative and happy life would come to an abrupt halt when his entire family is deported to a concentration camp during World War II. While locked up he tries to convince his son that the whole thing is just a game.\",\"release_date\":\"1997-12-20\"},{\"popularity\":23.072,\"vote_count\":2774,\"video\":false,\"poster_path\":\"\\/yEBwuhuB3WyBCgukyDELRwwWNEz.jpg\",\"id\":311,\"adult\":false,\"backdrop_path\":\"\\/vnT6HzjLSDrAweHn9xWykb8Ii6T.jpg\",\"original_language\":\"en\",\"original_title\":\"Once Upon a Time in America\",\"genre_ids\":[80,18],\"title\":\"Once Upon a Time in America\",\"vote_average\":8.4,\"overview\":\"A former Prohibition-era Jewish gangster returns to the Lower East Side of Manhattan over thirty years later, where he once again must confront the ghosts and regrets of his old life.\",\"release_date\":\"1984-05-23\"},{\"popularity\":28.314,\"vote_count\":17344,\"video\":false,\"poster_path\":\"\\/yE5d3BUhE8hCnkMUJOo1QDoOGNz.jpg\",\"id\":13,\"adult\":false,\"backdrop_path\":\"\\/tNVTWNa8cxZLw7aWvHzSDLyc7ig.jpg\",\"original_language\":\"en\",\"original_title\":\"Forrest Gump\",\"genre_ids\":[35,18,10749],\"title\":\"Forrest Gump\",\"vote_average\":8.4,\"overview\":\"A man with a low IQ has accomplished great things in his life and been present during significant historic events—in each case, far exceeding what anyone imagined he could do. But despite all he has achieved, his one true love eludes him.\",\"release_date\":\"1994-07-06\"},{\"popularity\":35.808,\"vote_count\":14987,\"video\":false,\"poster_path\":\"\\/rCzpDGLbOoPwLjy3OAm5NUPOTrC.jpg\",\"id\":122,\"adult\":false,\"backdrop_path\":\"\\/vL5zJK4p5CmyjyVsxcdhmeo7J1J.jpg\",\"original_language\":\"en\",\"original_title\":\"The Lord of the Rings: The Return of the King\",\"genre_ids\":[28,12,14],\"title\":\"The Lord of the Rings: The Return of the King\",\"vote_average\":8.4,\"overview\":\"Aragorn is revealed as the heir to the ancient kings as he, Gandalf and the other members of the broken fellowship struggle to save Gondor from Sauron's forces. Meanwhile, Frodo and Sam take the ring closer to the heart of Mordor, the dark lord's realm.\",\"release_date\":\"2003-12-01\"},{\"popularity\":49.496,\"vote_count\":21561,\"video\":false,\"poster_path\":\"\\/qJ2tW6WMUDux911r6m7haRef0WH.jpg\",\"id\":155,\"adult\":false,\"backdrop_path\":\"\\/cfT29Im5VDvjE0RpyKOSdCKZal7.jpg\",\"original_language\":\"en\",\"original_title\":\"The Dark Knight\",\"genre_ids\":[28,80,18,53],\"title\":\"The Dark Knight\",\"vote_average\":8.4,\"overview\":\"Batman raises the stakes in his war on crime. With the help of Lt. Jim Gordon and District Attorney Harvey Dent, Batman sets out to dismantle the remaining criminal organizations that plague the streets. The partnership proves to be effective, but they soon find themselves prey to a reign of chaos unleashed by a rising criminal mastermind known to the terrified citizens of Gotham as the Joker.\",\"release_date\":\"2008-07-16\"},{\"popularity\":26.149,\"id\":769,\"video\":false,\"vote_count\":6994,\"vote_average\":8.4,\"title\":\"GoodFellas\",\"release_date\":\"1990-09-12\",\"original_language\":\"en\",\"original_title\":\"GoodFellas\",\"genre_ids\":[18,80],\"backdrop_path\":\"\\/sw7mordbZxgITU877yTpZCud90M.jpg\",\"adult\":false,\"overview\":\"The true story of Henry Hill, a half-Irish, half-Sicilian Brooklyn kid who is adopted by neighbourhood gangsters at an early age and climbs the ranks of a Mafia family under the guidance of Jimmy Conway.\",\"poster_path\":\"\\/pwpGfTImTGifEGgLb3s6LRPd4I6.jpg\"},{\"popularity\":23.56,\"id\":389,\"video\":false,\"vote_count\":4486,\"vote_average\":8.4,\"title\":\"12 Angry Men\",\"release_date\":\"1957-04-10\",\"original_language\":\"en\",\"original_title\":\"12 Angry Men\",\"genre_ids\":[18],\"backdrop_path\":\"\\/6396QiokQBiTgPgB16nUWxYdM7N.jpg\",\"adult\":false,\"overview\":\"The defense and the prosecution have rested and the jury is filing into the jury room to decide if a young Spanish-American is guilty or innocent of murdering his father. What begins as an open and shut case soon becomes a mini-drama of each of the jurors' prejudices and preconceptions about the trial, the accused, and each other.\",\"poster_path\":\"\\/3W0v956XxSG5xgm7LB6qu8ExYJ2.jpg\"},{\"popularity\":27.015,\"vote_count\":4812,\"video\":false,\"poster_path\":\"\\/sN4h2uODctPePR8ot611XK3fdbs.jpg\",\"id\":429,\"adult\":false,\"backdrop_path\":\"\\/jh0I62ZU1z88p7TSv9KrF4nJ6xV.jpg\",\"original_language\":\"it\",\"original_title\":\"Il buono, il brutto, il cattivo\",\"genre_ids\":[37],\"title\":\"The Good, the Bad and the Ugly\",\"vote_average\":8.4,\"overview\":\"While the Civil War rages between the Union and the Confederacy, three men – a quiet loner, a ruthless hit man and a Mexican bandit – comb the American Southwest in search of a strongbox containing $200,000 in stolen gold.\",\"release_date\":\"1966-12-23\"}]}";

    // Declare variables
    private String tmdbApiKey;
    private TextView errorMassageTextView;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // retrieve TMDB API Key from assets folder
        tmdbApiKey = getTmdbApiKey();
        errorMassageTextView = findViewById(R.id.tv_error_message);
        if (tmdbApiKey.equals("")) {
            errorMassageTextView.setVisibility(View.VISIBLE);
            errorMassageTextView.setText("API KEY is not available.\nPlease correct and Try again!");
        } else {
            errorMassageTextView.setVisibility(View.GONE);
        }
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        String[] myString = {tmdbApiKey,NetworkUtilities.TMDB_POPULAR_REQUEST_URL,"1"};
        new FetchMoviesTask().execute(myString);
    }

    private String getTmdbApiKey () {
        String apiKey = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("tmdb_key.txt")));
            apiKey = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiKey;
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            /* Without information we cannot look up Movies. */
            if (params.length == 0) {
                return null;
            }

            URL movieRequestUrl = NetworkUtilities.buildUrl(params[0], params[1], params[2]);

            try {
                String jsonMovieResponse = NetworkUtilities
                        .getResponseFromHttpUrl(movieRequestUrl);

//                String[] simpleJsonWeatherData = OpenWeatherJsonUtils
//                        .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);
//
//                return simpleJsonWeatherData;
                return jsonMovieResponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {

                // TODO send movieData to RecyclerView
//                showWeatherDataView();
//                mForecastAdapter.setWeatherData(weatherData);
            } else {

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemThatWasSelected = item.getItemId();
        switch (menuItemThatWasSelected) {
            case R.id.action_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
