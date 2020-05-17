INSERT INTO `nation` VALUES ('1', 'ITA', 'Italy');
INSERT INTO `nation` VALUES ('2', 'GER', 'Germany');
INSERT INTO `nation` VALUES ('3', 'ESP', 'Spain');
INSERT INTO `nation` VALUES ('4', 'FRA', 'France');
INSERT INTO `nation` VALUES ('5', 'ENG', 'England');


INSERT INTO `league` VALUES ('1',1,'SERIE A', '1');
INSERT INTO `league` VALUES ('2',0, 'SERIE B', '1');
INSERT INTO `league` VALUES ('3',1, 'BUNDESLIGA', '2');
INSERT INTO `league` VALUES ('4', 1,'PREMIER LEAGUE', '5');
INSERT INTO `league` VALUES ('5', 1,'LIGUE 1', '4');
INSERT INTO `league` VALUES ('6', 1,'LALIGA', '3');

INSERT INTO `player` VALUES ('1' ,  '1989' ,'bonucci@gmail.com' , 'Leonardo' , 'Bonucci');
INSERT INTO `player` VALUES ('2' ,  '1989' ,'chiellini@gmail.com' ,  'Giorgio' , 'Chiellini');
INSERT INTO `player` VALUES ('3' ,  '1989' ,'romagnoli@gmail.com' ,  'Alessio' , 'Romagnoli');
INSERT INTO `player` VALUES ('4' ,  '1989' ,'mancini@gmail.com' ,  'Gianluca' , 'Mancini');
INSERT INTO `player` VALUES ('5' , '1989' , 'messi@gmail.com' ,  'Leo' , 'Messi');
INSERT INTO `player` VALUES ('6' ,  '1989' ,'ronaldo@gmail.com'  , 'Cristiano' , 'Ronaldo');
INSERT INTO `player` VALUES ('7' ,  '1989' ,'hazard@gmail.com'  , 'Eden' , 'Hazard');

INSERT INTO `season` VALUES ('1' , '2020/01/09' , '2019/07/06');
INSERT INTO `season` VALUES ('2' , '2019/01/09' , '2018/07/06');
INSERT INTO `season` VALUES ('3' , '2018/01/09' , '2017/07/06');
INSERT INTO `season` VALUES ('4' , '2017/01/09' , '2016/07/06');
INSERT INTO `season` VALUES ('5' , '2016/01/09' , '2015/07/06');
INSERT INTO `season` VALUES ('6' , '2015/01/09' , '2014/07/06');
INSERT INTO `season` VALUES ('7' , '2014/01/09' , '2013/07/06');


INSERT INTO `team` VALUES ('1', 'Turin', 'Torino');
INSERT INTO `team` VALUES ('2', 'Turin', 'Juventus');
INSERT INTO `team` VALUES ('3', 'Milan', 'Milan');
INSERT INTO `team` VALUES ('4', 'London', 'Chelsea');
INSERT INTO `team` VALUES ('5', 'Monaco', 'Bayern Monaco');
INSERT INTO `team` VALUES ('6', 'Madrid', 'Real Madrid');
INSERT INTO `team` VALUES ('7', 'Lion', 'Lion');
INSERT INTO `team` VALUES ('10', 'Genova', 'Sampdoria');
INSERT INTO `team` VALUES ('11', 'Lecce', 'Lecce');
INSERT INTO `team` VALUES ('12', 'Sassuolo', 'Sassuolo');
INSERT INTO `team` VALUES ('13', 'Milano', 'Inter');
INSERT INTO `team` VALUES ('14', 'Roma', 'Lazio');
INSERT INTO `team` VALUES ('15', 'Roma', 'Roma');
INSERT INTO `team` VALUES ('16', 'Barcelloma', 'Barcellona');
INSERT INTO `team` VALUES ('17', 'Siviglia', 'Siviglia');
INSERT INTO `team` VALUES ('18', 'Valencia', 'Valencia');
INSERT INTO `team` VALUES ('19', 'Paris', 'PSG');
INSERT INTO `team` VALUES ('20', 'Marsiglia', 'Marsiglia');
INSERT INTO `team` VALUES ('21', 'Lille', 'Lille');
INSERT INTO `team` VALUES ('22', 'Dortmund', 'BorussiaDortmund');
INSERT INTO `team` VALUES ('23', 'Lipsia', 'RasenBallsportLeipzig');
INSERT INTO `team` VALUES ('24', 'Leverkusen', 'BayerLeverkusenFutball');


INSERT INTO `team_season_league` VALUES ('1','1','1');
INSERT INTO `team_season_league` VALUES ('2','1','2');
INSERT INTO `team_season_league` VALUES ('3','1','1');
INSERT INTO `team_season_league` VALUES ('4','1','1');
INSERT INTO `team_season_league` VALUES ('10', '3', '1');
INSERT INTO `team_season_league` VALUES ('11', '3', '1');
INSERT INTO `team_season_league` VALUES ('12', '3', '1');
INSERT INTO `team_season_league` VALUES ('13', '3', '1');
INSERT INTO `team_season_league` VALUES ('14', '3', '1');
INSERT INTO `team_season_league` VALUES ('15', '3', '1');
INSERT INTO `team_season_league` VALUES ('22', '3', '3');
INSERT INTO `team_season_league` VALUES ('23', '3', '3');
INSERT INTO `team_season_league` VALUES ('5', '3', '3');
INSERT INTO `team_season_league` VALUES ('24', '3', '3');
INSERT INTO `team_season_league` VALUES ('19', '3', '5');
INSERT INTO `team_season_league` VALUES ('20', '3', '5');
INSERT INTO `team_season_league` VALUES ('21', '3', '5');
INSERT INTO `team_season_league` VALUES ('7', '3', '5');
INSERT INTO `team_season_league` VALUES ('6', '3', '6');
INSERT INTO `team_season_league` VALUES ('16', '3', '6');
INSERT INTO `team_season_league` VALUES ('17', '3', '6');
INSERT INTO `team_season_league` VALUES ('18', '3', '6');

INSERT INTO `player_season_team` VALUES ('1','1','1','1');
INSERT INTO `player_season_team` VALUES ('2','1','1','2');
INSERT INTO `player_season_team` VALUES ('3','2','1','1');
INSERT INTO `player_season_team` VALUES ('4','2','1','2');
INSERT INTO `player_season_team` VALUES ('5','3','1','3');
INSERT INTO `player_season_team` VALUES ('6','4','1','4');
INSERT INTO `player_season_team` VALUES ('7','5','1','3');
INSERT INTO `player_season_team` VALUES ('8','6','1','6');

