/* Creant Nacionalitat */
CREATE TABLE IF NOT EXISTS nacionalitat (
        `codi` varchar(3),
        `nom` varchar(250),
        CONSTRAINT `nacionalitat_pk` PRIMARY KEY (`codi`),
        CONSTRAINT `nacionalitat_nn_nom` CHECK (`nom`is not null)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* Creant Taula Users */
CREATE TABLE  IF NOT EXISTS users (
        `id` int(3) AUTO_INCREMENT,
	`email` varchar(320) NOT NULL,
	`nom` varchar(40) NOT NULL,
        `cognoms` varchar(100) NOT NULL,
        `password` varchar(64) NOT NULL,
        `data_naix` date NOT NULL,
        `genere` char(1) NOT NULL,
        `telefon` varchar(20),
        `bloquejat` boolean DEFAULT false,
        `nacionalitat` varchar(3) NOT NULL,
        `role` int(1) NOT NULL DEFAULT 0,
        `token` varchar(64) NOT NULL,
        `validat` boolean NOT NULL DEFAULT false,
        CONSTRAINT users_pk PRIMARY KEY (`id`),
        CONSTRAINT users_uq_email UNIQUE (`email`),
        CONSTRAINT users_ck_genere CHECK (`genere` in ('H','D','I')),
        CONSTRAINT users_fk_nacionalitat FOREIGN KEY (`nacionalitat`) REFERENCES nacionalitat(`codi`),
        CONSTRAINT users_ck_role CHECK ( `role` >= 0 ),
        INDEX activitat_idx_email (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* Creant Taula Calendari */
CREATE TABLE  IF NOT EXISTS calendari (
        `id` int(3) auto_increment,
        `nom` varchar(250) NOT NULL,
        `data_creacio` datetime NOT NULL,
        `user` int(3) NOT NULL,
        CONSTRAINT calendari_pk PRIMARY KEY (`id`),
        CONSTRAINT calendari_ck_dt_creacio CHECK (`data_creacio` <= sysdate()),
        CONSTRAINT calendari_fk_users FOREIGN KEY (`user`) REFERENCES users(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* Creant Taula Tipus Activitat */
CREATE TABLE IF NOT EXISTS tipus_activitat (
        `codi` int(4) AUTO_INCREMENT,
        `nom` varchar(250),
        `user` int(3),
        CONSTRAINT tipus_activitat_pk PRIMARY KEY (`codi`, `user`),
        CONSTRAINT tipus_activitat_nn_nom CHECK (`nom` is not null),
        CONSTRAINT tipus_activitat_fk_users FOREIGN KEY (`user`) REFERENCES users(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* Creant Taula Activitat */
CREATE TABLE  IF NOT EXISTS activitat (
        `calendari` int(3),
        `id` int(4) AUTO_INCREMENT,
        `nom` varchar(250),
        `data_inici` datetime NOT NULL,
        `data_fi` datetime,
        `descripcio` varchar(500),
        `tipus` int(4) NOT NULL,
        `user` int(3) NOT NULL,
        `publicada` boolean DEFAULT false,
        CONSTRAINT activitat_pk PRIMARY KEY(`id`,`calendari`),
        CONSTRAINT activitat_nn_nom CHECK (`nom`  is not null),
        CONSTRAINT activitat_ck_dt_fi CHECK ((`data_fi` is null) or (`data_fi` is not null and `data_fi` >= `data_inici`)),
        CONSTRAINT activitat_fk_calendari FOREIGN KEY (`calendari`) REFERENCES calendari(`id`),
        CONSTRAINT activitat_fk_tipus_activitat FOREIGN KEY (`tipus`) REFERENCES tipus_activitat(`codi`),
        CONSTRAINT activitat_fk_users FOREIGN KEY (`user`) REFERENCES users(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* Creant Taula Calendari Target */
CREATE TABLE IF NOT EXISTS calendari_target (
        `email` varchar(320),
        `calendar` int(3),
        CONSTRAINT calendari_target_pk PRIMARY KEY (`calendar`,`email`),
        CONSTRAINT calendari_target_fk_calendari FOREIGN KEY (`calendar`) REFERENCES calendari(`id`) ON DELETE CASCADE 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/* Creant Taula Ajuda */
CREATE TABLE IF NOT EXISTS ajuda (
        `user` int,
        `calendari` int(3),
        CONSTRAINT conte_pk PRIMARY KEY (`user`, `calendari`),
        CONSTRAINT conte_fk_users FOREIGN KEY (`user`) REFERENCES users(`id`) ON DELETE CASCADE,
        CONSTRAINT conte_Fk_calendari FOREIGN KEY (`calendari`) REFERENCES calendari(`id`) ON DELETE CASCADE
);

/* Inseritnt totes les Nacionalitats a la taula nacionalitat */
INSERT INTO `nacionalitat` (`codi`, `nom`) VALUES
('AFG', 'Afghan'),
('ALB', 'Albanian'),
('ATA', 'Antarctic'),
('DZA', 'Algerian'),
('ASM', 'American Samoan'),
('AND', 'Andorran'),
('AGO', 'Angolan'),
('ATG', 'Antiguan or Barbudan'),
('AZE', 'Azerbaijani, Azeri'),
('ARG', 'Argentine'),
('AUS', 'Australian'),
('AUT', 'Austrian'),
('BHS', 'Bahamian'),
('BHR', 'Bahraini'),
('BGD', 'Bangladeshi'),
('ARM', 'Armenian'),
('BRB', 'Barbadian'),
('BEL', 'Belgian'),
('BMU', 'Bermudian, Bermudan'),
('BTN', 'Bhutanese'),
('BOL', 'Bolivian'),
('BIH', 'Bosnian or Herzegovinian'),
('BWA', 'Motswana, Botswanan'),
('BVT', 'Bouvet Island'),
('BRA', 'Brazilian'),
('BLZ', 'Belizean'),
('IOT', 'BIOT'),
('SLB', 'Solomon Island'),
('VGB', 'British Virgin Island'),
('BRN', 'Bruneian'),
('BGR', 'Bulgarian'),
('MMR', 'Burmese'),
('BDI', 'Burundian'),
('BLR', 'Belarusian'),
('KHM', 'Cambodian'),
('CMR', 'Cameroonian'),
('CAN', 'Canadian'),
('CPV', 'Cabo Verdean'),
('CYM', 'Caymanian'),
('CAF', 'Central African'),
('LKA', 'Sri Lankan'),
('TCD', 'Chadian'),
('CHL', 'Chilean'),
('CHN', 'Chinese'),
('TWN', 'Chinese, Taiwanese'),
('CXR', 'Christmas Island'),
('CCK', 'Cocos Island'),
('COL', 'Colombian'),
('COM', 'Comoran, Comorian'),
('MYT', 'Mahoran'),
('COG', 'Congolese'),
('COD', 'Congolese'),
('COK', 'Cook Island'),
('CRI', 'Costa Rican'),
('HRV', 'Croatian'),
('CUB', 'Cuban'),
('CYP', 'Cypriot'),
('CZE', 'Czech'),
('BEN', 'Beninese, Beninois'),
('DNK', 'Danish'),
('DMA', 'Dominican'),
('DOM', 'Dominican'),
('ECU', 'Ecuadorian'),
('SLV', 'Salvadoran'),
('GNQ', 'Equatorial Guinean, Equatoguinean'),
('ETH', 'Ethiopian'),
('ERI', 'Eritrean'),
('EST', 'Estonian'),
('FRO', 'Faroese'),
('FLK', 'Falkland Island'),
('SGS', 'South Georgia or South Sandwich Islands'),
('FJI', 'Fijian'),
('FIN', 'Finnish'),
('ALA', '�land Island'),
('FRA', 'French'),
('GUF', 'French Guianese'),
('PYF', 'French Polynesian'),
('ATF', 'French Southern Territories'),
('DJI', 'Djiboutian'),
('GAB', 'Gabonese'),
('GEO', 'Georgian'),
('GMB', 'Gambian'),
('PSE', 'Palestinian'),
('DEU', 'German'),
('GHA', 'Ghanaian'),
('GIB', 'Gibraltar'),
('KIR', 'I-Kiribati'),
('GRC', 'Greek, Hellenic'),
('GRL', 'Greenlandic'),
('GRD', 'Grenadian'),
('GLP', 'Guadeloupe'),
('GUM', 'Guamanian, Guambat'),
('GTM', 'Guatemalan'),
('GIN', 'Guinean'),
('GUY', 'Guyanese'),
('HTI', 'Haitian'),
('HMD', 'Heard Island or McDonald Islands'),
('VAT', 'Vatican'),
('HND', 'Honduran'),
('HKG', 'Hong Kong, Hong Kongese'),
('HUN', 'Hungarian, Magyar'),
('ISL', 'Icelandic'),
('IND', 'Indian'),
('IDN', 'Indonesian'),
('IRN', 'Iranian, Persian'),
('IRQ', 'Iraqi'),
('IRL', 'Irish'),
('ISR', 'Israeli'),
('ITA', 'Italian'),
('CIV', 'Ivorian'),
('JAM', 'Jamaican'),
('JPN', 'Japanese'),
('KAZ', 'Kazakhstani, Kazakh'),
('JOR', 'Jordanian'),
('KEN', 'Kenyan'),
('PRK', 'North Korean'),
('KOR', 'South Korean'),
('KWT', 'Kuwaiti'),
('KGZ', 'Kyrgyzstani, Kyrgyz, Kirgiz, Kirghiz'),
('LAO', 'Lao, Laotian'),
('LBN', 'Lebanese'),
('LSO', 'Basotho'),
('LVA', 'Latvian'),
('LBR', 'Liberian'),
('LBY', 'Libyan'),
('LIE', 'Liechtenstein'),
('LTU', 'Lithuanian'),
('LUX', 'Luxembourg, Luxembourgish'),
('MAC', 'Macanese, Chinese'),
('MDG', 'Malagasy'),
('MWI', 'Malawian'),
('MYS', 'Malaysian'),
('MDV', 'Maldivian'),
('MLI', 'Malian, Malinese'),
('MLT', 'Maltese'),
('MTQ', 'Martiniquais, Martinican'),
('MRT', 'Mauritanian'),
('MUS', 'Mauritian'),
('MEX', 'Mexican'),
('MCO', 'Mon�gasque, Monacan'),
('MNG', 'Mongolian'),
('MDA', 'Moldovan'),
('MNE', 'Montenegrin'),
('MSR', 'Montserratian'),
('MAR', 'Moroccan'),
('MOZ', 'Mozambican'),
('OMN', 'Omani'),
('NAM', 'Namibian'),
('NRU', 'Nauruan'),
('NPL', 'Nepali, Nepalese'),
('NLD', 'Dutch, Netherlandic'),
('CUW', 'Cura�aoan'),
('ABW', 'Aruban'),
('SXM', 'Sint Maarten'),
('BES', 'Bonaire'),
('NCL', 'New Caledonian'),
('VUT', 'Ni-Vanuatu, Vanuatuan'),
('NZL', 'New Zealand, NZ'),
('NIC', 'Nicaraguan'),
('NER', 'Nigerien'),
('NGA', 'Nigerian'),
('NIU', 'Niuean'),
('NFK', 'Norfolk Island'),
('NOR', 'Norwegian'),
('MNP', 'Northern Marianan'),
('UMI', 'American'),
('FSM', 'Micronesian'),
('MHL', 'Marshallese'),
('PLW', 'Palauan'),
('PAK', 'Pakistani'),
('PAN', 'Panamanian'),
('PNG', 'Papua New Guinean, Papuan'),
('PRY', 'Paraguayan'),
('PER', 'Peruvian'),
('PHL', 'Philippine, Filipino'),
('PCN', 'Pitcairn Island'),
('POL', 'Polish'),
('PRT', 'Portuguese'),
('GNB', 'Bissau-Guinean'),
('TLS', 'Timorese'),
('PRI', 'Puerto Rican'),
('QAT', 'Qatari'),
('REU', 'R�unionese, R�unionnais'),
('ROU', 'Romanian'),
('RUS', 'Russian'),
('RWA', 'Rwandan'),
('BLM', 'Barth�lemois'),
('SHN', 'Saint Helenian'),
('KNA', 'Kittitian or Nevisian'),
('AIA', 'Anguillan'),
('LCA', 'Saint Lucian'),
('MAF', 'Saint-Martinoise'),
('SPM', 'Saint-Pierrais or Miquelonnais'),
('VCT', 'Saint Vincentian, Vincentian'),
('SMR', 'Sammarinese'),
('STP', 'S�o Tom�an'),
('SAU', 'Saudi, Saudi Arabian'),
('SEN', 'Senegalese'),
('SRB', 'Serbian'),
('SYC', 'Seychellois'),
('SLE', 'Sierra Leonean'),
('SGP', 'Singaporean'),
('SVK', 'Slovak'),
('VNM', 'Vietnamese'),
('SVN', 'Slovenian, Slovene'),
('SOM', 'Somali, Somalian'),
('ZAF', 'South African'),
('ZWE', 'Zimbabwean'),
('ESP', 'Spanish'),
('SSD', 'South Sudanese'),
('SDN', 'Sudanese'),
('ESH', 'Sahrawi, Sahrawian, Sahraouian'),
('SUR', 'Surinamese'),
('SJM', 'Svalbard'),
('SWZ', 'Swazi'),
('SWE', 'Swedish'),
('CHE', 'Swiss'),
('SYR', 'Syrian'),
('TJK', 'Tajikistani'),
('THA', 'Thai'),
('TGO', 'Togolese'),
('TKL', 'Tokelauan'),
('TON', 'Tongan'),
('TTO', 'Trinidadian or Tobagonian'),
('ARE', 'Emirati, Emirian, Emiri'),
('TUN', 'Tunisian'),
('TUR', 'Turkish'),
('TKM', 'Turkmen'),
('TCA', 'Turks and Caicos Island'),
('TUV', 'Tuvaluan'),
('UGA', 'Ugandan'),
('UKR', 'Ukrainian'),
('MKD', 'Macedonian'),
('EGY', 'Egyptian'),
('GBR', 'British, UK'),
('GGY', 'Channel Island'),
('JEY', 'Channel Island'),
('IMN', 'Manx'),
('TZA', 'Tanzanian'),
('USA', 'American'),
('VIR', 'U.S. Virgin Island'),
('BFA', 'Burkinab�'),
('URY', 'Uruguayan'),
('UZB', 'Uzbekistani, Uzbek'),
('VEN', 'Venezuelan'),
('WLF', 'Wallis and Futuna, Wallisian or Futunan'),
('WSM', 'Samoan'),
('YEM', 'Yemeni'),
('ZMB', 'Zambian');



