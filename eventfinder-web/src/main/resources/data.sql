INSERT INTO user_settings (id, email_notification_enabled, android_notification_enabled, event_notification_enabled, advertiser_notification_enabled, location_notification_enabled) VALUES (1, true, true, true, true, true);
INSERT INTO user_settings (id, email_notification_enabled, android_notification_enabled, event_notification_enabled, advertiser_notification_enabled, location_notification_enabled) VALUES (2, true, true, true, true, true);
INSERT INTO user_settings (id, email_notification_enabled, android_notification_enabled, event_notification_enabled, advertiser_notification_enabled, location_notification_enabled) VALUES (3, true, true, true, true, true);
INSERT INTO user_settings (id, email_notification_enabled, android_notification_enabled, event_notification_enabled, advertiser_notification_enabled, location_notification_enabled) VALUES (4, true, true, true, true, true);

INSERT INTO user (id, email, name, password, member_since, settings_id) VALUES (1, 'user.email@address.com', 'Példa User', 'jelszó', '2018-05-01 10:30:00', 1);
INSERT INTO user (id, email, name, password, member_since, settings_id) VALUES (2, 'user2.email@address.com', 'user2', 'p', '2012-07-01 10:30:00', 2);
INSERT INTO user (id, email, name, password, member_since, settings_id) VALUES (3, 'user1.email@address.com', 'user1', 'password', '2010-07-01 10:30:00', 3);
INSERT INTO user (id, email, name, password, member_since, settings_id) VALUES (4, 'eva.vegh.bp@gmail.com', 'x', 'x', '2018-07-01 10:30:00', 4);

INSERT INTO location (id, name, country, city, zip_code, address, lat, lon) VALUES (1, 'Csodák Palotája', 'Magyarország', 'Budapest', '1222', 'Nagytétényi út 37-43.', 47.40633500, 19.01517500);
INSERT INTO location (id, name, country, city, zip_code, address, lat, lon) VALUES (2, NULL, 'Magyarország', 'Budapest', '1095', 'Komor Marcell u. 1.', 47.46970600, 19.07120800);
INSERT INTO location (id, name, country, city, zip_code, address, lat, lon) VALUES (3, 'Rex Kutyaotthon', 'Magyarország', 'Budapest', '1048', 'Óceánárok u. 33.', 47.58734600, 19.10326600);
INSERT INTO location (id, name, country, city, zip_code, address, lat, lon) VALUES (4, 'Budapest Bábszínház', 'Magyarország', 'Budapest', '1062', 'Andrássy út 62.', 47.50700500, 19.06553800);

INSERT INTO event (id, name, location_id, starts_at, ends_at, summary, description, web_url, fb_url, created_by) VALUES (1, 'Varázslatos tudományteszt', 1, '2018-08-01 10:30:00', '2018-08-03 10:30:00', 'A csodák palotája története', 'A természettudományok - s azokon belül is elsősorban a fizika - felejthetetlen, legendás hírű népszerűsítője Öveges József volt, aki sokszor álmodott arról, hogy lesz majd Magyarországon egy olyan intézmény, ahol a látogatók a \"világ érdekes\" jelenségei közül nagyon sokkal közvetlen kapcsolatba kerülhetnek, kedvükre kísérletezhetnek, s \"megérinthetik a tudományt\". Öveges József ezt a képzeletbeli házat a CSODÁK PALOTÁJÁNAK nevezte.', 'http://csopa.hu', NULL, 1);
INSERT INTO event (id, name, location_id, starts_at, ends_at, summary, description, web_url, fb_url, created_by) VALUES (2, 'Családi délelőtt', 2, '2018-07-01 10:30:00', '2018-07-01 10:30:00', 'változatos programkínálat', 'A Családi Délelőtt minden szombaton 10:30 és 12:30 óra között tartalmas, változatos programkínálattal szeretne maradandó élményt nyújtani az egész családnak, a gyerekeknek, a szülőknek és a nagyszülőknek egyaránt.', NULL, NULL, 1);
INSERT INTO event (id, name, location_id, starts_at, ends_at, summary, description, web_url, fb_url, created_by) VALUES (3, 'Rex Kutyaotthon Állatsziget', 3, '2018-06-10 10:30:00', '2018-07-01 10:30:00', 'Budapesti Állat- és Természetvédelmi Élménypark', 'Milyen jó lenne, ha a kedves olvasó azonnal tudná, mire gondolunk, ha ezt a szót hallja: Állatsziget. Reméljük a Rex Kutyaotthon Alapítvány által életre hívott létesítmény átadásával 2004.06.26.-ától értelmet kapott a kifejezés, és egyre többen fogják megismerni azt a civil irányítású intézményt, melyet teljes nevén Budapesti Állat- és Természetvédelmi Élményparknak kereszteltünk el.', 'http://rex.hu', NULL, 2);
INSERT INTO event (id, name, location_id, starts_at, ends_at, summary, description, web_url, fb_url, created_by) VALUES (4, 'Báb előadás', 4, '2018-05-20 10:30:00', '2018-05-21 10:30:00', 'A Világörökség részének nyilvánított Andrássy út közepén, 100 évnél régebbi 8 emeletes műemléképületben játszik a 60 éves Budapest Bábszínház, Közép-Európa legnagyobb bábszínháza.', '\"A 403 férőhelyes, minden korszerű színpadtechnikai berendezéssel felszerelt színháztermen kívül az épület IV. emeleten található a 99 néző befogadására alkalmas, variálható nézőterű, intim hatású kamaraterem, a Játszó-Tér. Így aratott világsikert a Csongor és Tünde, a Szentivánéji álom, a Vihar és Az ember tragédiája, csakúgy, mint Beckett némajátékai, Mrozek bábpantomimje, Dürrenmatt tragikomédiája, Petőfi János vitéze.\"', 'http://budapest-babszinhaz.hu', 'https://www.facebook.com/babszinhaz/', 1);
INSERT INTO event (id, name, location_id, starts_at, ends_at, summary, description, web_url, fb_url, created_by) VALUES (5, 'Teszt', 1, '2018-10-29 19:05:00', '2018-08-03 10:30:00', 'A csodák palotája története', 'A természettudományok - s azokon belül is elsősorban a fizika - felejthetetlen, legendás hírű népszerűsítője Öveges József volt, aki sokszor álmodott arról, hogy lesz majd Magyarországon egy olyan intézmény, ahol a látogatók a \"világ érdekes\" jelenségei közül nagyon sokkal közvetlen kapcsolatba kerülhetnek, kedvükre kísérletezhetnek, s \"megérinthetik a tudományt\". Öveges József ezt a képzeletbeli házat a CSODÁK PALOTÁJÁNAK nevezte.', 'http://csopa.hu', NULL, 1);

INSERT INTO price (id, event_id, currency, amount, type) VALUES (1, 1, 'HUF', 1000.00, 'NORMAL');
INSERT INTO price (id, event_id, currency, amount, type) VALUES (2, 1, 'HUF', 800.00, 'STUDENT');
INSERT INTO price (id, event_id, currency, amount, type) VALUES (3, 2, 'HUF', 600.00, NULL);
INSERT INTO price (id, event_id, currency, amount, type) VALUES (4, 4, 'HUF', 800.00, NULL);
INSERT INTO price (id, event_id, currency, amount, type) VALUES (5, 4, 'USD', 400, NULL);
INSERT INTO price (id, event_id, currency, amount, type) VALUES (6, 4, 'EUR', 300, NULL);

INSERT INTO event_type (event_id, type) VALUES (1, 'KIDS');
INSERT INTO event_type (event_id, type) VALUES (1, 'EXHIBITION');
INSERT INTO event_type (event_id, type) VALUES (2, 'EXHIBITION');
INSERT INTO event_type (event_id, type) VALUES (3, 'OUTDOOR');
INSERT INTO event_type (event_id, type) VALUES (3, 'OTHER');
INSERT INTO event_type (event_id, type) VALUES (4, 'KIDS');
INSERT INTO event_type (event_id, type) VALUES (4, 'CULTURE');
INSERT INTO event_type (event_id, type) VALUES (4, 'THEATRE');

INSERT INTO user_role(user_id, role) VALUES(3, 'ADMIN');
INSERT INTO user_role(user_id, role) VALUES(2, 'SUPERADMIN');
