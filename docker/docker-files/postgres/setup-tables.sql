------------------------------------------------------------------

-- EASYSOLUTIONS DETECTID %%version%% INSTALLATION SCRIPT

------------------------------------------------------------------


--
-- Core Tables
--
CREATE TABLE global_lock_description(
  gld_id          SERIAL NOT NULL,
  gld_name        VARCHAR(50)          NOT NULL,
  gld_content VARCHAR(300)         NOT NULL
);

ALTER TABLE global_lock_description ADD CONSTRAINT PK_global_lock_description PRIMARY KEY (gld_id);

INSERT INTO  global_lock_description(gld_id,gld_name,gld_content) VALUES(10,'Custom lock',' ');
INSERT INTO  global_lock_description(gld_id,gld_name,gld_content) VALUES(11,'General lock','The client has been locked by the administrator or an external system');
INSERT INTO  global_lock_description(gld_id,gld_name,gld_content) VALUES(12,'Failed attempts lock','The client has been locked because the maximum number of failed authentication attempts was reached');

ALTER SEQUENCE public.global_lock_description_gld_id_seq RESTART WITH 13;

CREATE TABLE client (
  cli_id_client   BIGSERIAL PRIMARY KEY,
  cli_description VARCHAR(256) NOT NULL,
  cli_date_added  TIMESTAMP,
  cli_shared_key  VARCHAR(256) UNIQUE,
  cli_email       VARCHAR(256),
  cli_status      INT DEFAULT 1010,
  cli_validation_attempts INT DEFAULT 0,
  cli_last_activity_date BIGINT,
  gld_id          INTEGER
);
CREATE INDEX client_cli_description_key ON client (cli_description);

CREATE TABLE dmc_admin_user (
  adu_id_admin_user         BIGSERIAL PRIMARY KEY,
  adu_name                  VARCHAR(256),
  adu_last_name             VARCHAR(256),
  adu_pass                  VARCHAR(256),
  adu_email                 VARCHAR(256),
  adu_phone                 VARCHAR(256),
  adu_i18n_code             CHARACTER(2) DEFAULT 'en' :: bpchar,
  adu_profile               INT,
  adu_status                INT,
  adu_status_update_time    TIMESTAMP(0) WITHOUT TIME ZONE,
  adu_login_name            CHARACTER VARYING NOT NULL,
  adu_active_directory_user BOOLEAN           NOT NULL DEFAULT FALSE
);

CREATE TABLE audits (
  aud_id_audits BIGSERIAL PRIMARY KEY,
  aud_info      TEXT,
  aud_timestamp TIMESTAMP,
  aud_entity_id VARCHAR(512),
  aud_user_id   VARCHAR(50),
  aud_operation INT,
  aud_entity    INT,
  aud_ip        VARCHAR(45) ,
  ch_id         INTEGER,
  operation_id  BIGINT
);

CREATE TABLE detect_common_configurations (
  dcc_identifier   VARCHAR(256) PRIMARY KEY,
  dcc_conf_content TEXT DEFAULT '' :: TEXT NOT NULL,
  dcc_updated_at   TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE dmc_users_config (
  duc_user_id    INT          NOT NULL,
  duc_conf_name  VARCHAR(256) NOT NULL,
  duc_conf_value BYTEA        NOT NULL
);

CREATE TABLE eventtype (
  evt_id_eventtype INT PRIMARY KEY,
  evt_name         VARCHAR(64) UNIQUE NOT NULL,
  evt_description  VARCHAR(256)       NOT NULL
);

INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('client.history.mashup.limit', '20');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('max_qty_page', '50');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('max_qty_page_export', '5000');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('allowed_users', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('licenseJobTrigger_CE', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('audit.clean.period', '0 0 0 0 3 ?');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('machineHistoric.clean.period', '0 0 0 0 3 ?');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('otbMailHistory.clean.period', '0 0 0 0 3 ?');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('otbsmsHistory.clean.period', '0 0 0 0 3 ?');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('easysolTokensLog.clean.period', '0 0 0 0 3 ?');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('thirdPartyTokensLog.clean.period', '0 0 0 0 3 ?');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('deletedMachines.clean.period', '0 0 0 0 3 ?');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('cleaningHistoryTrigger_CE', '0 0 0 23 * ?');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('detect_license', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('license_end_date', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('licensedModules', '4079');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('license_status', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('central.server.key.module', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('central.server.key.exponent', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('pintoken_otp_length', '6');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('pintoken_enable', 'false');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('challengeQuestionHistoric.clean.period', '0 0 0 18 2 ?');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('identisiteHistoric.clean.period', '0 0 0 18 2 ?');
---
--  SMTP Config
---
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('did.mail.server.id', '');

---
-- Easy Grid Cards
---
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('alertNotificationEmail', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('enableEmailNotification', 'false');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('isSettedGridCardConfiguration', 'false');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('gridCardConfigurationRows', '0');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('gridCardConfigurationColumns', '0');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('gridCardConfigurationCellSize', '0');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('gridCardConfigurationCellType', 'ALPHA_NUMERIC');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('gridCardConfigurationSerialPrefix', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('gridCardConfigurationSerialSize', '0');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('auditCleanTrigger_CE', '0 * * * * ? 2099');

INSERT INTO eventtype (evt_id_eventtype, evt_name, evt_description) VALUES (1, 'START', 'Start');
INSERT INTO eventtype (evt_id_eventtype, evt_name, evt_description) VALUES (2, 'ACTIVATE', 'Activate');
INSERT INTO eventtype (evt_id_eventtype, evt_name, evt_description) VALUES (3, 'DEACTIVATE', 'Deactivate');
INSERT INTO eventtype (evt_id_eventtype, evt_name, evt_description) VALUES (4, 'DELETE', 'Delete');
INSERT INTO eventtype (evt_id_eventtype, evt_name, evt_description) VALUES (5, 'CONNECT', 'Successful Connect');
INSERT INTO eventtype (evt_id_eventtype, evt_name, evt_description) VALUES (6, 'OTP_LOGIN', 'Login with an OTP');
INSERT INTO eventtype (evt_id_eventtype, evt_name, evt_description) VALUES (7, 'OTP_LOGIN_FAIL', 'Failed login with an OTP');
INSERT INTO eventtype (evt_id_eventtype, evt_name, evt_description) VALUES (8, 'OTP_ASSIGNED', 'OTP assigned to a client');
INSERT INTO eventtype (evt_id_eventtype, evt_name, evt_description) VALUES (9, 'OTP_DEASSIGNED', 'OTP Deassigned from a client');

INSERT INTO dmc_admin_user (adu_name, adu_last_name, adu_pass, adu_email, adu_phone, adu_i18n_code, adu_profile, adu_status, adu_login_name, adu_active_directory_user)
  VALUES ('Administrator', 'Admin', '2d61272edf6c14fabaedbba3f19d84f0d1195e5a', 'detect-admin', '', 'en', 1, 1, 'detect-admin', FALSE);

--
-- End Core Tables
--



--
-- Machine Authentication Tables
--

CREATE TABLE machine (
  mac_id_machine   BIGSERIAL PRIMARY KEY,
  mac_id_client    BIGINT REFERENCES client (cli_id_client),
  mac_fingerprint  VARCHAR(256) NOT NULL,
  mac_start_date   TIMESTAMP(0) NOT NULL,
  mac_end_date     TIMESTAMP(0) WITHOUT TIME ZONE,
  mac_description  VARCHAR(256),
  mac_active       BOOLEAN DEFAULT TRUE,
  mac_last_connect TIMESTAMP WITHOUT TIME ZONE,
  mac_xml          BYTEA
);

CREATE UNIQUE INDEX unique_client_fingerprint ON machine USING BTREE (mac_id_client, mac_fingerprint);


CREATE TABLE machine_historic (
  mch_id_machine_historic BIGSERIAL PRIMARY KEY,
  mch_id_machine          INT,
  mch_timestamp           TIMESTAMP,
  mch_id_eventtype        INT NOT NULL
);


CREATE TABLE detectid_enrollment_cache (
  dec_transaction_id         VARCHAR(256),
  dec_enrollment_information VARCHAR,
  dec_creation_time          TIMESTAMP(0)
);
CREATE INDEX dec_transaction_id_key ON detectid_enrollment_cache (dec_transaction_id);


CREATE TABLE detectid_process_cache (
  dpc_id_detectid_process VARCHAR(256),
  dpc_fingerprint         VARCHAR(256) NOT NULL,
  dpc_creation_time       TIMESTAMP    NOT NULL,
  dpc_processes           VARCHAR
);
CREATE INDEX dpc_id_detectid_process_key ON detectid_process_cache (dpc_id_detectid_process);

CREATE TABLE black_list (
  bli_fingerprint VARCHAR(900) PRIMARY KEY
);


CREATE TABLE detectid_blacklist_cache (
  dbc_id_detectid_blacklist VARCHAR(256) PRIMARY KEY,
  dbc_fingerprint           VARCHAR(256) NOT NULL,
  dbc_creation_time         TIMESTAMP    NOT NULL
);

CREATE TABLE black_list_attempt (
  bla_id_black_list_attempt BIGSERIAL PRIMARY KEY,
  bla_content               VARCHAR,
  bla_timestamp             TIMESTAMP,
  bla_fingerprint           VARCHAR(256),
  bla_shared_key            VARCHAR(256)
);

CREATE TABLE ssu_downloads (
  ssd_id_ssu_download    BIGSERIAL PRIMARY KEY,
  ssd_download_timestamp TIMESTAMP WITHOUT TIME ZONE,
  ssd_content            BYTEA,
  ssd_count              INT DEFAULT 0,
  ssd_applied            BOOLEAN DEFAULT FALSE,
  ssd_type               VARCHAR(32) DEFAULT 'BLACK_LIST'
);

CREATE TABLE warning_machine_status (
  wms_id_warning_machine_status INT PRIMARY KEY,
  wms_name                      VARCHAR(50),
  wms_description               VARCHAR(1024)
);

CREATE TABLE warning_machine (
  wma_id_warning_machine        BIGSERIAL PRIMARY KEY,
  wma_fingerprint               VARCHAR(1000),
  wma_id_warning_machine_status INT REFERENCES warning_machine_status (wms_id_warning_machine_status),
  wma_last_update               TIMESTAMP,
  wma_description               VARCHAR(1024),
  wma_quantity                  INT
);

CREATE TABLE enrollment_attempt (
  eat_id_connect_attempt BIGSERIAL PRIMARY KEY,
  eat_fingerprint        VARCHAR(128),
  eat_timestamp          TIMESTAMP WITH TIME ZONE,
  eat_id_client          BIGINT NOT NULL
);

CREATE TABLE detectid_client_config (
  dic_id_client           BIGSERIAL PRIMARY KEY,
  dic_max_machine_allowed INT DEFAULT 2
);

CREATE TABLE deleted_machine (
  dmc_id_machine  BIGINT                      NOT NULL,
  dmc_id_client   INTEGER                     NOT NULL,
  dmc_fingerprint CHARACTER VARYING(256)      NOT NULL,
  dmc_description CHARACTER VARYING(256),
  dmc_active      BOOLEAN,
  dmc_delete_date TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE error_log (
  erl_id_error_log BIGSERIAL PRIMARY KEY,
  erl_content      VARCHAR NOT NULL,
  erl_timestamp    TIMESTAMP(0)
);

CREATE TABLE custom_validation (
  cuv_id_client BIGINT PRIMARY KEY,
  cuv_failures  INT DEFAULT 1
);

INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('clearProcessIDCacheTrigger_CE', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('uploadWarningMachinesJobTrigger_CE', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('clearEnrollProcessIDCacheTrigger_CE', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('ssu_version', '31');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('enrollments_attempts_to_black', '3');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('enrollments_attempts_to_warning', '2');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('black_by_wrongs', 'false');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('machineDefaultActive', 'true');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('save_additional_info', 'true');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('downloadBlacklistJobTrigger_CE', '0 1 18 * * ?');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('wrongs_answers_to_black', '3');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('wrongs_answers_to_warning', '1');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('black_by_enrollments', 'false');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('warning_by_enrollments', 'false');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('defaultMaxAllowedMachines', '2');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('warning_by_wrongs', 'true');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('appliedSSUByDefault', 'true');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('custom_to_warning', '3');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('warning_by_custom', 'true');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('custom_to_black', '5');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('black_by_custom', 'true');

INSERT INTO warning_machine_status (wms_id_warning_machine_status, wms_name, wms_description) VALUES (1, 'SUSPICIOUS', 'Suspicious');
INSERT INTO warning_machine_status (wms_id_warning_machine_status, wms_name, wms_description) VALUES (2, 'WARNING', 'Warning');
INSERT INTO warning_machine_status (wms_id_warning_machine_status, wms_name, wms_description) VALUES (3, 'POTENTIALLY', 'Potentially dangerous');
INSERT INTO warning_machine_status (wms_id_warning_machine_status, wms_name, wms_description) VALUES (4, 'DANGEROUS', 'Highly dangerous');

ALTER TABLE ONLY warning_machine
ADD CONSTRAINT machine_black_list_mbl_id_black_list_status_fkey FOREIGN KEY (wma_id_warning_machine_status) REFERENCES warning_machine_status (wms_id_warning_machine_status);

ALTER TABLE detectid_client_config
ADD CONSTRAINT dcc_fk_client FOREIGN KEY (dic_id_client)
REFERENCES client (cli_id_client) MATCH SIMPLE
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE custom_validation
ADD CONSTRAINT cuv_fk_client FOREIGN KEY (cuv_id_client)
REFERENCES client (cli_id_client) MATCH SIMPLE
ON UPDATE CASCADE ON DELETE CASCADE;

--
-- End Machine Authentication Tables
--



--
-- Identisite Tables
--

CREATE TABLE identisite (
  ide_id_identisite    BIGINT PRIMARY KEY,
  ide_mime_type        VARCHAR,
  ide_binary_content   BYTEA,
  ide_description      VARCHAR(256),
  ide_add_date         TIMESTAMP,
  ide_active           BOOLEAN DEFAULT TRUE,
  ide_file_name        VARCHAR(256) UNIQUE NOT NULL,
  ide_image_protection INTEGER
);

CREATE TABLE client_identisite
(
  cid_id_client     BIGINT  NOT NULL,
  cid_id_identisite INTEGER NOT NULL,
  cid_description   CHARACTER VARYING DEFAULT '' :: CHARACTER VARYING,
  CONSTRAINT cid_client_identisite_pkey PRIMARY KEY (cid_id_client, cid_id_identisite),
  CONSTRAINT client_identisite_cid_id_client_fkey FOREIGN KEY (cid_id_client)
  REFERENCES client (cli_id_client) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT client_identisite_cid_id_identisite_fkey FOREIGN KEY (cid_id_identisite)
  REFERENCES identisite (ide_id_identisite) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE identisite_historic (
  idh_id_identisite_historic BIGSERIAL PRIMARY KEY,
  idh_id_client              INT8        NOT NULL,
  idh_timestamp              TIMESTAMP(0),
  idh_id_eventtype           VARCHAR(50) NOT NULL,
  idh_id_image               INTEGER
);

--
-- End Identisite Tables
--



--
-- CQ Tables
--

-- MASTER QUESTION'S GROUP --
CREATE TABLE cq_group_question (
  gpq_id_group_question BIGSERIAL PRIMARY KEY,
  gpq_name_text VARCHAR(100),
  gpq_description_text VARCHAR(256)
);
CREATE UNIQUE INDEX group_question_key_text ON cq_group_question USING BTREE (gpq_name_text);

INSERT INTO cq_group_question (gpq_name_text, gpq_description_text) VALUES ('General', 'Default group for challenge questions');
-- MASTER QUESTION'S GROUP --

CREATE TABLE cq_master_question (
  mqs_id_master_question BIGSERIAL PRIMARY KEY,
  mqs_key_text           VARCHAR(256),
  mqs_id_group_questions BIGINT REFERENCES cq_group_question (gpq_id_group_question)
);
CREATE UNIQUE INDEX master_question_key_text ON cq_master_question USING BTREE (UPPER(mqs_key_text));


CREATE TABLE challenge_question_historic (
  cqh_id_historic          BIGSERIAL PRIMARY KEY,
  cqh_correct              BOOLEAN,
  cqh_timestamp_throw      TIMESTAMP(0),
  cqh_id_challengequestion BIGINT NOT NULL,
  cqh_id_client            BIGINT NOT NULL,
  cqh_timestamp_answered   TIMESTAMP(0) WITHOUT TIME ZONE
);


CREATE TABLE cq_challengequestion (
  chq_id_challengequestion BIGSERIAL PRIMARY KEY,
  chq_id_client            INT REFERENCES client (cli_id_client),
  chq_answer               VARCHAR(256) NOT NULL,
  chq_id_master_question   INT REFERENCES cq_master_question (mqs_id_master_question)
);
CREATE UNIQUE INDEX challenge_question_unique ON cq_challengequestion USING BTREE (chq_id_master_question, chq_id_client);


CREATE TABLE cq_last_question (
  lqs_id_last_question     BIGSERIAL PRIMARY KEY,
  lqs_id_challengequestion BIGINT NOT NULL,
  lqs_id_client            BIGINT REFERENCES client (cli_id_client),
  lqs_tries                INT DEFAULT 1
);

CREATE TABLE cq_custom_question (
  cuq_id_custom_question BIGSERIAL PRIMARY KEY,
  cuq_id_client          BIGINT REFERENCES client (cli_id_client),
  cuq_text               VARCHAR(256) NOT NULL,
  cuq_answer             VARCHAR(256) NOT NULL,
  cuq_active             BOOLEAN DEFAULT TRUE
);

--
-- End CQ Tables
--



--
-- OTP Vasco Tables
--

CREATE TABLE otp_historic (
  oth_id_otp_historic BIGSERIAL PRIMARY KEY,
  otp_timestamp       TIMESTAMP              NOT NULL,
  otp_serial          CHARACTER VARYING(128) NOT NULL,
  otp_id_client       INTEGER,
  otp_id_eventtype    INTEGER                NOT NULL,
  CONSTRAINT otp_historic_otp_id_client_fkey FOREIGN KEY (otp_id_client)
  REFERENCES client (cli_id_client) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE SET NULL,
  CONSTRAINT otp_historic_otp_id_eventtype_fkey FOREIGN KEY (otp_id_eventtype)
  REFERENCES eventtype (evt_id_eventtype) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE vascotoken (
  token_vasco_id   BIGSERIAL PRIMARY KEY,
  vdstokenserial   VARCHAR(22)    NOT NULL,
  vdsimportdate    CHARACTER(50),
  vdsimporttime    CHARACTER(50),
  vdsassigneduser  INTEGER,
  vdstokenblob     CHARACTER(250) NOT NULL,
  vdsstaticvector  CHARACTER(250),
  vdshsmstoragekey CHARACTER(50),
  vdsprovider      INTEGER,
  vdsfilename      VARCHAR(500),
  vdstokenmodel    VARCHAR(100),
  token_enabled    INTEGER
);

CREATE TABLE vascotoken_log
(
  vascotoken_log_id      SERIAL                   NOT NULL,
  client_id              BIGINT,
  token_serial           CHARACTER VARYING,
  log_time               TIMESTAMP WITH TIME ZONE NOT NULL,
  log_success            CHARACTER VARYING        NOT NULL,
  client_sharedkey       VARCHAR(100) DEFAULT '0' NOT NULL,
  transaction_identifier VARCHAR(100),
  user_name              VARCHAR(100),
  CONSTRAINT vascotoken_log_pk PRIMARY KEY (vascotoken_log_id)
);

CREATE TABLE kernelparameters (
  applname       CHARACTER(50) PRIMARY KEY,
  itimewindow    CHARACTER(50),
  stimewindow    CHARACTER(50),
  diaglevel      CHARACTER(50),
  gmtadjust      CHARACTER(50),
  checkchallenge CHARACTER(50),
  ithreshold     CHARACTER(50),
  sthreshold     CHARACTER(50),
  chkinactdays   CHARACTER(50),
  derivevector   CHARACTER(50),
  syncwindow     CHARACTER(50),
  onlinesg       CHARACTER(50),
  eventwindow    CHARACTER(50),
  hsmslotid      CHARACTER(50),
  storagekeyid   CHARACTER(50),
  transportkeyid CHARACTER(50),
  storagekey1    CHARACTER(50),
  storagekey2    CHARACTER(50),
  storagekey3    CHARACTER(50),
  storagekey4    CHARACTER(50)
);


--
-- End OTP Vasco Tables
--

--
-- OTB Mail Tables
--

CREATE TABLE otbmail_history (
  otbmail_history_id          BIGSERIAL PRIMARY KEY,
  cli_id_client               INT8         NOT NULL,
  otbmail_history_timestamp   TIMESTAMP    NOT NULL,
  otbmail_history_event_type  VARCHAR(50)  NOT NULL,
  otbmail_history_client_mail VARCHAR(256) NULL,
  channel_name       VARCHAR(100)
);

CREATE TABLE otbmail_client (
  otbmail_client_id                BIGSERIAL PRIMARY KEY,
  cli_id_client                    INT8                 NOT NULL,
  otbmail_client_email             VARCHAR(100)         NOT NULL,
  otbmail_client_last_mod          TIMESTAMP            NOT NULL,
  otbmail_client_is_active         INT DEFAULT 1 NOT NULL
);
CREATE INDEX otbmail_client_config_index_x_cli_id_client ON otbmail_client (cli_id_client);

CREATE TABLE otbmail_otp (
  otbmail_otp_generated_otp VARCHAR(50) NOT NULL,
  otbmail_otp_creation_time TIMESTAMP   NOT NULL,
  cli_shared_key            VARCHAR(256),
  otbmail_otp_retries_count INT2        NOT NULL,
  otbmail_otp_id_otp        BIGSERIAL   NOT NULL,
  otbmail_otp_contextbind  VARCHAR(100) NULL,
  ch_id                       INTEGER
);
CREATE INDEX otbmail_otp_cli_shared_key_and_otp_key ON otbmail_otp (cli_shared_key, otbmail_otp_generated_otp);


--
-- End OTB Mail Tables
--

--
-- OTB SMS Tables
--

CREATE TABLE otbsms_client (
  otbsms_client_id                BIGSERIAL PRIMARY KEY,
  cli_id_client                   INT4                 NOT NULL,
  otbsms_client_phonenumber       VARCHAR(30)          NOT NULL,
  otbsms_client_last_mod          TIMESTAMP            NOT NULL,
  otbsms_client_is_active         INT DEFAULT 1 NOT NULL
);

CREATE TABLE otbsms_history (
  otbsms_history_id                  BIGSERIAL PRIMARY KEY,
  cli_id_client                      BIGINT      NOT NULL,
  otbsms_history_timestamp           TIMESTAMP   NOT NULL,
  otbsms_history_event_type          VARCHAR(50) NOT NULL,
  otbsms_history_client_number       VARCHAR(30) NOT NULL,
  channel_name                       VARCHAR(100)
);

CREATE TABLE otbsms_otp (
  otbsms_otp_id_otp        BIGSERIAL    NOT NULL,
  otbsms_otp_generated_otp VARCHAR(50)  NOT NULL,
  otbsms_otp_creation_time TIMESTAMP    NOT NULL,
  cli_shared_key           VARCHAR(256),
  otbsms_otp_retries_count INT2         NOT NULL,
  otbsms_otp_purpose       VARCHAR(140) NOT NULL,
  otbsms_otp_contextBind   VARCHAR(100) NULL,
  ch_id                    INTEGER
);
CREATE INDEX otbsms_otp_cli_shared_key_and_otp_key ON otbsms_otp (cli_shared_key, otbsms_otp_generated_otp);


INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('radius.enabled', 'false');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('radius.accounting.port', '1813');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('radius.authentication.port', '1812');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('radius.name', 'detectid_radius_server');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('radius.NAS', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('radius.snmp.enabled', 'true');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('radius.snmp.manager', '127.0.0.1');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('radius.snmp.read', 'public');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('radius.snmp.trap', 'public');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('radius.snmp.write', 'private');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('radius.proxyServer', 'default');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('radius.proxy.authPort', '1812');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('radius.proxy.secret', 'secret');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('active.directory.enabled', 'false');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('active.directory.port', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('active.directory.server', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('active.directory.userDn', '');
--
-- End OTB SMS Tables
--


--
-- Easysol Tokens Tables
--

CREATE TABLE easysol_token
(
  easy_token_serial       CHARACTER VARYING        NOT NULL,
  easy_token_private_data CHARACTER VARYING(1024)  NOT NULL,
  easy_token_client_id    BIGINT,
  easy_token_import_date  TIMESTAMP WITH TIME ZONE,
  easy_token_enabled      BOOLEAN                  NOT NULL DEFAULT FALSE,
  easy_token_pin          VARCHAR(1024)            NOT NULL,
  easy_token_type         VARCHAR(10) DEFAULT '100',
  easy_token_algorithm    VARCHAR(9)               NOT NULL DEFAULT 'old',
  easy_token_active_info  VARCHAR(50) DEFAULT '0',
  easy_token_active_code  VARCHAR(100) DEFAULT '0' NOT NULL,
  easy_token_assign_date  TIMESTAMP WITH TIME ZONE,
  CONSTRAINT easysol_token_pkey PRIMARY KEY (easy_token_serial),
  CONSTRAINT easysol_client_fk FOREIGN KEY (easy_token_client_id)
  REFERENCES client (cli_id_client) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE easysol_log
(
  easy_log_id      SERIAL                   NOT NULL,
  easy_log_client  BIGINT,
  easy_log_token   CHARACTER VARYING,
  easy_log_time    TIMESTAMP WITH TIME ZONE NOT NULL,
  easy_log_success CHARACTER VARYING        NOT NULL,
  easy_client      VARCHAR(100) DEFAULT '0' NOT NULL,
  CONSTRAINT easy_log_pk PRIMARY KEY (easy_log_id)
);

--
-- End Easysol Tokens Tables
--

---
-- Easy Authentication
---

CREATE TABLE ea_roles
(
  ear_id_role       SERIAL                NOT NULL,
  ear_name          CHARACTER VARYING(50) NOT NULL,
  ear_template      BOOLEAN               NOT NULL DEFAULT FALSE,
  ear_from_template INTEGER,
  CONSTRAINT ea_roles_pkey PRIMARY KEY (ear_id_role),
  CONSTRAINT ear_template_fk FOREIGN KEY (ear_from_template)
  REFERENCES ea_roles (ear_id_role) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT ea_roles_ear_name_key UNIQUE (ear_name)
);

CREATE TABLE ea_user_roles
(
  eaur_id_admin_user INTEGER NOT NULL,
  eaur_id_role       INTEGER NOT NULL,
  CONSTRAINT eaur_pk PRIMARY KEY (eaur_id_admin_user, eaur_id_role),
  CONSTRAINT eaur_role_fk FOREIGN KEY (eaur_id_role)
  REFERENCES ea_roles (ear_id_role) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE ea_granted_permissions
(
  eapg_view_name  CHARACTER VARYING NOT NULL,
  eapg_id_role    INTEGER           NOT NULL,
  eapg_mask_value INTEGER           NOT NULL DEFAULT 0,
  CONSTRAINT eagp_pk PRIMARY KEY (eapg_view_name, eapg_id_role),
  CONSTRAINT ea_permissionsxviewgranted_eapg_id_role_fkey FOREIGN KEY (eapg_id_role)
  REFERENCES ea_roles (ear_id_role) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE CASCADE
);


---
-- Easy Grid Cards
---
CREATE TABLE grid_card_issue (
  gci_id_grid_card_issue BIGSERIAL PRIMARY KEY,
  gci_issue_date         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  gci_quantity           INT                         NOT NULL,
  gci_unassigned         INT                         NOT NULL,
  gci_start_code         VARCHAR(32),
  gci_end_code           VARCHAR(32),
  gci_assigned           INT                         NOT NULL,
  gci_removed            INT                         NOT NULL,
  gci_file_available     BOOLEAN DEFAULT FALSE
);

CREATE TABLE grid_card (
  gcd_id_grid_card       BIGSERIAL PRIMARY KEY,
  gcd_identifier         VARCHAR(32) UNIQUE,
  gcd_state              VARCHAR(16) CHECK ( gcd_state IN ('NOT_ASSIGNED', 'ASSIGNED', 'REMOVED')) NOT NULL,
  gcd_grid               TEXT                                                                      NOT NULL,
  gcd_id_grid_card_issue BIGINT REFERENCES grid_card_issue (gci_id_grid_card_issue)                NOT NULL
);

CREATE TABLE client_grid_card (
  cgc_id_client    INT REFERENCES client (cli_id_client) UNIQUE,
  cgc_id_grid_card BIGINT REFERENCES grid_card (gcd_id_grid_card) UNIQUE,
  PRIMARY KEY (cgc_id_client, cgc_id_grid_card)
);

CREATE TABLE questions_client_grid_card (
  qcg_id_client           INT REFERENCES client (cli_id_client) PRIMARY KEY,
  qcg_questions_grid_card VARCHAR(1024) NOT NULL,
  qcg_date_correct_answer TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE log_client_grid_card (
  lcg_id_grid_card    INT,
  lcg_id_client       INT,
  PRIMARY KEY (lcg_id_client, lcg_id_grid_card),
  lcg_date_assigned   TIMESTAMP WITHOUT TIME ZONE                               NOT NULL,
  lcg_date_issue      TIMESTAMP WITHOUT TIME ZONE                               NOT NULL,
  lcg_date_discharged TIMESTAMP WITHOUT TIME ZONE,
  lcd_identifier      VARCHAR(128)                                              NOT NULL,
  lcd_state           VARCHAR(16) CHECK ( lcd_state IN ('ASSIGNED', 'REMOVED')) NOT NULL
);

CREATE TABLE log_questions_client_grid_card (
  lqc_id_log_questions_client_grid_card BIGSERIAL PRIMARY KEY,
  lqc_id_grid_card                      BIGINT                      NOT NULL,
  lqc_number_correct_answer             INT                         NOT NULL DEFAULT 0, lqc_number_incorrect_answer INT NOT NULL DEFAULT 0,
  lqc_date_throw                        TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  lqc_date_last_answer                  TIMESTAMP WITHOUT TIME ZONE
);

CREATE SEQUENCE crypto_profile_id_seq;
CREATE SEQUENCE grid_card_issue_conf_id_seq;
CREATE SEQUENCE grid_card_issue_file_id_seq;

CREATE TABLE grid_card_issue_configuration (
  gcc_id_config     INT4 DEFAULT NEXTVAL('grid_card_issue_conf_id_seq'),
  gcc_name_config   VARCHAR(25) NOT NULL,
  gcc_active        BOOLEAN     NOT NULL,
  gcc_store_file    BOOLEAN     NULL,
  gcc_sign          BOOLEAN     NOT NULL,
  gcc_crypt         BOOLEAN     NOT NULL,
  gcc_id_profile    INT4        NULL,
  gcc_ftp_transfer  BOOLEAN     NOT NULL,
  gcc_ip_transfer   VARCHAR(50) NULL,
  gcc_path_transfer VARCHAR(50) NULL,
  gcc_user_ftp      VARCHAR(25) NULL,
  gcc_user_pass_ftp VARCHAR(64) NULL,
  gcc_creation_date TIMESTAMP   NOT NULL,
  gcc_user          INT4        NULL,
  PRIMARY KEY (gcc_id_config)
);

CREATE TABLE dmc_crypto_profile (
  dgc_id_profile    INT4 DEFAULT NEXTVAL('crypto_profile_id_seq'),
  dgc_id_user       INT4        NOT NULL,
  dgc_creation_date TIMESTAMP   NOT NULL,
  dgc_profile_name  VARCHAR(50) NOT NULL,
  dgc_provider_name VARCHAR(50) NULL,
  dgc_public_key    BYTEA       NULL,
  dgc_private_key   BYTEA       NULL,
  dgc_key_ext       VARCHAR(3)  NULL,
  dgc_internal_name VARCHAR(25) NULL,
  dgc_identity_name VARCHAR(50) NULL,
  dgc_passphrase    VARCHAR(64) NULL,
  dgc_cert_external BOOLEAN     NOT NULL,
  dgc_active        BOOLEAN     NOT NULL,
  PRIMARY KEY (dgc_id_profile)
);

CREATE TABLE grid_card_issue_file (
  gcf_id_file            INT8 DEFAULT NEXTVAL('grid_card_issue_file_id_seq'),
  gcf_id_grid_card_issue INT8         NOT NULL,
  gcf_name_file          VARCHAR(40)  NOT NULL,
  gcf_id_profile         INT4         NULL,
  gcf_encrypted          BOOLEAN      NOT NULL,
  gcf_file_transfered    BOOLEAN      NOT NULL,
  gcf_state              INT4         NOT NULL,
  gcf_error_message      VARCHAR(500) NULL,
  gcf_creation_date      TIMESTAMP    NOT NULL,
  PRIMARY KEY (gcf_id_file)
);

INSERT INTO grid_card_issue_configuration (gcc_name_config, gcc_active, gcc_store_file, gcc_sign, gcc_crypt, gcc_id_profile, gcc_ftp_transfer, gcc_ip_transfer, gcc_path_transfer, gcc_user_ftp, gcc_user_pass_ftp, gcc_creation_date, gcc_user) VALUES ('DEFAULT', FALSE, FALSE, FALSE, FALSE, null, FALSE, null, null, null, null, current_timestamp, 1);
INSERT INTO grid_card_issue_configuration (gcc_name_config, gcc_active, gcc_store_file, gcc_sign, gcc_crypt, gcc_id_profile, gcc_ftp_transfer, gcc_ip_transfer, gcc_path_transfer, gcc_user_ftp, gcc_user_pass_ftp, gcc_creation_date, gcc_user) VALUES ('CUSTOM', TRUE, TRUE, FALSE, FALSE, null, FALSE, null, null, null, null, current_timestamp, 1);

-- Soap Historic
CREATE SEQUENCE soap_id_header_seq;
CREATE TABLE soap_request_historic (
  soap_id_request_historic INTEGER DEFAULT NEXTVAL('soap_id_header_seq') NOT NULL PRIMARY KEY,
  soap_action              VARCHAR(256),
  soap_path                VARCHAR(256),
  soap_date                TIMESTAMP(0)                                  NOT NULL
);

-- easysol tokens
ALTER TABLE easysol_token DROP CONSTRAINT easysol_client_fk;
ALTER TABLE easysol_token ADD CONSTRAINT easysol_client_fk FOREIGN KEY (easy_token_client_id)
REFERENCES client (cli_id_client) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE EASYSOL_TOKEN
ALTER COLUMN easy_token_active_code TYPE VARCHAR(500);

ALTER TABLE EASYSOL_TOKEN ADD COLUMN est_last_otp VARCHAR(500);

ALTER TABLE EASYSOL_TOKEN ADD COLUMN est_out_of_sync INT;

ALTER TABLE EASYSOL_TOKEN ADD COLUMN est_url_code VARCHAR(50);

CREATE UNIQUE INDEX idx_unique_est_uc ON easysol_token (est_url_code);

CREATE TABLE otp_configuration (
  otc_id         BIGSERIAL PRIMARY KEY,
  otc_otp_length INT         NOT NULL DEFAULT 6,
  otc_ts_size    INT         NOT NULL DEFAULT 60,
  otc_ini_time   INT         NOT NULL DEFAULT 0,
  otc_del_wind   INT         NOT NULL DEFAULT 100,
  otc_alg_name   VARCHAR(30) NOT NULL DEFAULT 'HmacSHA1',
  otc_syn_steps  INT         NOT NULL DEFAULT 5
);

INSERT INTO otp_configuration (otc_ini_time) VALUES (0);

ALTER TABLE machine ADD COLUMN mac_type VARCHAR(50);
UPDATE machine
SET mac_type='HARDWARE';


-- TABLE AND INDEX FOR IP2LOCATION

CREATE TABLE ip2location (
  id           INTEGER NOT NULL,
  ip_from      BIGINT,
  ip_to        BIGINT,
  country_code CHARACTER VARYING(2),
  country_name CHARACTER VARYING(64),
  region       CHARACTER VARYING(128),
  city         CHARACTER VARYING(128),
  latitude     CHARACTER VARYING(50),
  longitude    CHARACTER VARYING(50),
  isp_name     CHARACTER VARYING(256)
);

CREATE SEQUENCE ip2location_id_seq;
ALTER TABLE ip2location ALTER COLUMN id SET DEFAULT nextval('ip2location_id_seq' :: REGCLASS);
CREATE INDEX ip_from_index ON ip2location USING BTREE (ip_from DESC);

INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('da.minimum.matching.percentage', '70');

-- END TABLE AND INDEX FOR IP2LOCATION

-- IP2PROXY TABLE

CREATE TABLE ip2proxy (
  id           INTEGER NOT NULL,
  ip_address CHARACTER VARYING(20),
  country_code CHARACTER VARYING(2),
  country_name CHARACTER VARYING(64)
);

CREATE SEQUENCE ip2proxy_id_seq;
ALTER TABLE ip2proxy ALTER COLUMN id SET DEFAULT nextval('ip2proxy_id_seq' :: REGCLASS);
CREATE INDEX ip_address_index ON ip2proxy USING BTREE (ip_address DESC);

-- END IP2PROXY TABLE

--MOBILE AUTHENTICATION

CREATE SEQUENCE mobile_transaction_table_id_seq;

CREATE TABLE mobile_auth_transactions
(
  id                        INT8 DEFAULT NEXTVAL('mobile_transaction_table_id_seq'),
  mobile_auth_transactions_id INT8                   NOT NULL,
  client_id                 INTEGER                NOT NULL,
  masked_fingerprint                 CHARACTER VARYING(500) NOT NULL,
  reg_date                  BIGINT                 NOT NULL,
  closure_date              BIGINT,
  status                    INTEGER                NOT NULL,
  attempts                  INTEGER                NOT NULL DEFAULT 0,
  notified                  INTEGER                NOT NULL DEFAULT 0,
  external_identifier    VARCHAR(100),
  ch_id                   integer not null default 1,
  PRIMARY KEY (mobile_auth_transactions_id)
);

CREATE TABLE mobile_auth_gen_actv_cod
(
  mobile_auth_gen_actv_cod_id CHARACTER VARYING(700) NOT NULL,
  client_id                 INTEGER                NOT NULL,
  generation_date           BIGINT                 NOT NULL,
  activation_date           BIGINT,
  status                    INTEGER                NOT NULL,
  attempts                  INTEGER,
  integrity_date            INTEGER         NOT NULL DEFAULT 0,
  ch_id                     INTEGER,
  PRIMARY KEY (mobile_auth_gen_actv_cod_id,integrity_date)
);

CREATE SEQUENCE mobile_auth_device_id_seq;

CREATE TABLE mobile_auth_device
(
  mobile_auth_device_id INT8 DEFAULT NEXTVAL('mobile_auth_device_id_seq'),
  client_id           INTEGER                     NOT NULL,
  device_id           CHARACTER VARYING(500)      NOT NULL,
  masked_fingerprint  CHARACTER VARYING(500)      NOT NULL,
  model               CHARACTER VARYING(100),
  operating_sys       INTEGER                     NOT NULL,
  description         CHARACTER VARYING(500),
  activated           INTEGER                     NOT NULL,
  registered_date     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  fingerprint         CHARACTER VARYING(500)      NOT NULL,
  keys_ref            INTEGER                     NOT NULL
);

ALTER TABLE mobile_auth_device ADD CONSTRAINT mobile_auth_device_pkey PRIMARY KEY (mobile_auth_device_id);
CREATE UNIQUE INDEX mobile_dev_uidx_keys_ref ON mobile_auth_device(keys_ref);
CREATE UNIQUE INDEX mobile_dev_uidx_client_finger  ON mobile_auth_device(client_id,fingerprint);


CREATE SEQUENCE mobile_auth_history_seq;

CREATE TABLE mobile_auth_history (
  id                 BIGINT DEFAULT NEXTVAL('mobile_auth_history_seq'),
  client_id          BIGINT       NOT NULL,
  history_time       TIMESTAMP    NOT NULL,
  history_event_type VARCHAR(100) NOT NULL,
  masked_fingerprint VARCHAR(500) NOT NULL,
  description        VARCHAR(500),
  channel_name       VARCHAR(100)
);


CREATE SEQUENCE mobile_auth_alert_msg_id_seq;

CREATE TABLE mobile_auth_alert_messages
(
  mau_alert_messages_id     INT8 DEFAULT NEXTVAL('mobile_auth_alert_msg_id_seq'),
  mobile_auth_alert_msg_id  INT8                   NOT NULL,
  prefix                    VARCHAR(3)  NULL,
  client_id                 INTEGER                NOT NULL,
  masked_fingerprint        CHARACTER VARYING(500) NOT NULL,
  external_identifier       VARCHAR(100),
  ch_id                     INTEGER NOT NULL DEFAULT 1,
  PRIMARY KEY (mobile_auth_alert_msg_id)
);

CREATE SEQUENCE mau_alert_msg_status_id_seq;

CREATE TABLE mobile_auth_alert_msg_status
(
  mau_alert_status_id                        INT8 DEFAULT NEXTVAL('mau_alert_msg_status_id_seq'),
  mau_alert_msg_id INT8                   NOT NULL,
  reg_date                  BIGINT                 NOT NULL,
  status                    INTEGER                NOT NULL,
  device_date             BIGINT,
  CONSTRAINT alert_msg_id_fk FOREIGN KEY (mau_alert_msg_id)
  REFERENCES mobile_auth_alert_messages (mobile_auth_alert_msg_id) MATCH SIMPLE
  ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('mobileHistory.clean.period', '0 0 0 0 3 ?');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('mobile.push.connection.pool.size.max', '100');

-- LDAP CONFIGURATION

INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('ldap.base_dn', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('ldap.bind_dn', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('ldap.bind_dn_password', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('ldap.use_ssl', 'false');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('ldap.type.data_source', 'AD');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('ldap.use_map_synchronize', 'false');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('ldap.synchronize_triggered', 'false');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('sinchronizeLDAPUsersTrigger_CE', '0 * * * * ? 2099');

-- END CONFIGURATION

-- LDAP SYNCHRONIZE

CREATE TABLE ldap_ou_groups_mapping (
  logm_base_dn    VARCHAR(256) NOT NULL,
  logm_group_name VARCHAR(256),
  logm_updated_at TIMESTAMP WITHOUT TIME ZONE,
  logm_id_role    INTEGER
);

-- END LDAP SYNCHRONIZE

-- CQ CASE SENSITIVE MODE

INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('challenge.question.casesensitive_validation_answers', 'true');

-- END CQ CASE SENSITIVE MODE

-- SECURITY KEYS

CREATE SEQUENCE public_security_rsa_keys_id_seq;

CREATE TABLE public_security_rsa_keys
(
  public_security_keys_id INT8 DEFAULT NEXTVAL('public_security_rsa_keys_id_seq'),
  rsa_pub_module          VARCHAR NOT NULL ,
  rsa_pub_exponent        VARCHAR NOT NULL ,
  constraint rsa_pub_unique_key unique (rsa_pub_module, rsa_pub_exponent),
  PRIMARY KEY (public_security_keys_id)
);

-- END SECURITY KEYS

-- KEYSTORE

INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('security.keystore.secret', '');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('security.keystore.data', '');
-- END KEYSTORE

--MOBILE TOKENS

CREATE TABLE mobile_token
(
  mobile_token_serial             CHARACTER VARYING         NOT NULL,
  mobile_token_init_private_data  CHARACTER VARYING(1024)   NOT NULL  UNIQUE,
  mobile_token_private_data       CHARACTER VARYING(1024)   NOT NULL,
  mobile_token_import_date        TIMESTAMP WITH TIME ZONE,
  mobile_token_status             INTEGER                   NOT NULL,
  mobile_token_algorithm          VARCHAR(9)                NOT NULL  DEFAULT 'TOTP',
  mobile_token_type               VARCHAR(10)                         DEFAULT 'Mobile',
  mobile_token_hash_code          VARCHAR(1024)              NOT NULL  DEFAULT '0',
  mobile_token_last_valid_otp     VARCHAR(500),
  mobile_token_out_of_sync        INT,
  mobile_auth_device_id           INT8,
  mobile_auth_gen_actv_cod_id     CHARACTER VARYING(700),
  mobile_auth_gen_integrity_date  INTEGER,
  mobile_auth_legacy_token 	      INTEGER DEFAULT 0,
  CONSTRAINT mobile_token_pkey PRIMARY KEY (mobile_token_serial)
);

--FK to Device Id
ALTER TABLE mobile_token
ADD CONSTRAINT mobile_token_device_fk FOREIGN KEY (mobile_auth_device_id)
REFERENCES mobile_auth_device (mobile_auth_device_id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION;

--FK to Mobile Activation Code
ALTER TABLE mobile_token
ADD CONSTRAINT mobile_token_act_code_fk FOREIGN KEY (mobile_auth_gen_actv_cod_id, mobile_auth_gen_integrity_date)
REFERENCES mobile_auth_gen_actv_cod (mobile_auth_gen_actv_cod_id, integrity_date) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION;

--END MOBILE TOKENS

--EMERGENCY PIN
CREATE SEQUENCE emergency_pin_id_seq;

CREATE TABLE emergency_pin(
  epin_id                   INT8 DEFAULT NEXTVAL('emergency_pin_id_seq'),
  epin_generated_pin        CHARACTER VARYING         NOT NULL,
  epin_generated_date       BIGINT,
  epin_closure_date         BIGINT,
  epin_attempts             INT NOT NULL DEFAULT 0,
  epin_valid_attempts       INT NOT NULL DEFAULT 0,
  epin_state                INT NOT NULL,
  epin_description          CHARACTER VARYING,
  cli_id_client             BIGSERIAL,
  CONSTRAINT emergency_pin_pkey PRIMARY KEY (epin_id)
);

--FK to Client
ALTER TABLE emergency_pin
ADD CONSTRAINT emergency_pin_client_fk FOREIGN KEY (cli_id_client)
REFERENCES client (cli_id_client) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION;

--Index to client and generated pin.
CREATE INDEX emergency_pin_idx_01 ON emergency_pin (cli_id_client, epin_generated_pin);

--Emergency pin history
CREATE SEQUENCE emergency_pin_history_id_seq;

CREATE TABLE emergency_pin_history(
  epin_id                   INT8 DEFAULT NEXTVAL('emergency_pin_history_id_seq'),
  epin_generated_pin        CHARACTER VARYING         NOT NULL,
  epin_generated_date       BIGINT,
  epin_closure_date         BIGINT,
  epin_attempts             INT NOT NULL DEFAULT 0,
  epin_valid_attempts       INT NOT NULL DEFAULT 0,
  epin_state                INT NOT NULL,
  epin_description          CHARACTER VARYING,
  cli_id_client             BIGSERIAL,
  CONSTRAINT emergency_pin_history_pkey PRIMARY KEY (epin_id)
);

--FK to Client
ALTER TABLE emergency_pin_history
ADD CONSTRAINT epin_history_client_fk FOREIGN KEY (cli_id_client)
REFERENCES client (cli_id_client) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION;
--Emergency pin configuration
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('emergency.pin.length', '6');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('emergency.pin.expiration.time', '86400000');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('emergency.pin.expiration.time.type', 'day');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('emergency.pin.max.failed.attempt', '5');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('emergency.pin.max.failed.attempt.unlimited', 'true');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('emergency.pin.characters.type', 'alpha');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('emergency.pin.characters.combination', 'lower');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('emergencyPinHistory.clean.period','0 0 0 0 3 ?');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('emergency.pin.max.valid.attempt', '1');

--END OF EMERGENCY PIN

--SERVICES AND FACTORS
CREATE TABLE service(
  service_id      INTEGER  NOT NULL,
  service_name    CHARACTER VARYING (100) NOT NULL,
  CONSTRAINT service_pkey  PRIMARY KEY (service_id)
);

insert into service (service_id, service_name) values (1, 'Mobile Authentication');
insert into service (service_id, service_name) values (2, 'Emergency Pin');
insert into service (service_id, service_name) values (3, 'Out-Of-Band');
insert into service (service_id, service_name) values (4, 'Easysol service');
insert into service (service_id, service_name) values (5, 'Easysol tokens');
insert into service (service_id, service_name) values (6, 'Challenge questions');
insert into service (service_id, service_name) values (7, 'Third party tokens');
insert into service (service_id, service_name) values (8, 'Machine identification');
insert into service (service_id, service_name) values (9, 'Grid Cards');

CREATE TABLE factor
(
  factor_id           INTEGER  NOT NULL,
  factor_name         CHARACTER VARYING (100) NOT NULL,
  factor_last_change  TIMESTAMP WITH TIME ZONE,
  service_id          INTEGER NOT NULL,
  CONSTRAINT factor_pkey  PRIMARY KEY (factor_id)
);

--FK to service
ALTER TABLE factor
ADD CONSTRAINT factor_service_fk FOREIGN KEY (service_id)
REFERENCES service (service_id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION;

-- add Foreign key to mobile_auth_transactions

ALTER TABLE mobile_auth_transactions ADD COLUMN factor_id INTEGER;
ALTER TABLE mobile_auth_transactions ADD CONSTRAINT factor_id_fk FOREIGN KEY (factor_id) REFERENCES factor (factor_id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION;
-- Add two values for requestAuthenticationToClient
ALTER TABLE mobile_auth_transactions ADD type varchar(50) default null;
ALTER TABLE mobile_auth_transactions ADD additionalInfo TEXT DEFAULT NULL;

INSERT INTO factor (factor_id, factor_name, factor_last_change,service_id) values (1, 'Push', now(),1);
INSERT INTO factor (factor_id, factor_name, factor_last_change,service_id) values (2, 'QR Code', now(),1);
INSERT INTO factor (factor_id, factor_name, factor_last_change,service_id) values (3, 'Mobile Token', now(),1);
insert into factor (factor_id, factor_name, factor_last_change, service_id) values (5, 'Emergency Pin', now(), 2);
insert into factor (factor_id, factor_name, factor_last_change, service_id) values (6, 'Out-Of-Band Mail', now(), 3);
insert into factor (factor_id, factor_name, factor_last_change, service_id) values (7, 'Out-Of-Band SMS', now(), 3);
insert into factor (factor_id, factor_name, factor_last_change, service_id) values (8, 'Out-Of-Band SMS and Mail', now(), 3);
insert into factor (factor_id, factor_name, factor_last_change, service_id) values (9, 'Easysol Factor', now(), 4);
insert into factor (factor_id, factor_name, factor_last_change, service_id) values (10, 'Hard tokens', now(), 5);
insert into factor (factor_id, factor_name, factor_last_change, service_id) values (11, 'Challenge questions', now(), 6);
insert into factor (factor_id, factor_name, factor_last_change, service_id) values (12, 'Third party tokens', now(), 7);
insert into factor (factor_id, factor_name, factor_last_change, service_id) values (13, 'Machine identification', now(), 8);
insert into factor (factor_id, factor_name, factor_last_change, service_id) values (14, 'Grid Cards', now(), 9);
insert into factor (factor_id, factor_name, factor_last_change, service_id) values (15, 'Mobile Authentication', now(), 1);

CREATE TABLE operation_factor(
  operation_name      CHARACTER VARYING (100) NOT NULL,
  factor_id           INTEGER  NOT NULL,
  CONSTRAINT operation_factor_pkey  PRIMARY KEY (operation_name)
);

--FK to factor
ALTER TABLE operation_factor
ADD CONSTRAINT operation_factor_fk FOREIGN KEY (factor_id)
REFERENCES factor (factor_id) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION;

insert into operation_factor(operation_name, factor_id) values ('net.easysol.emergency.pin.ws.WSEmergencyPinService.generatePin', 5);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.emergency.pin.ws.WSEmergencyPinService.validatePin', 5);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.pushauth.ws.WSPushAuthService.requestAuthenticationToClient', 1);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.mobileauth.ws.WSMobileAuthService.validateChallengeCode', 2);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.qrauth.ws.WSQRAuthService.requestAuthentication', 2);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.mobileauth.ws.impl.validation.MobileTokenValidation.validateMobileToken', 3);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.outofband.email.ws.WSOutOfBandMailService.createNewMailOtpCode', 6);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.outofband.email.ws.WSOutOfBandMailService.validateMailOtpCode', 6);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.outofband.email.ws.WSOutOfBandMailService.retrieveNewOTP', 6);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.outofband.sms.ws.WSOutOfBandSmsService.createNewSmsOtpCode', 7);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.outofband.sms.ws.WSOutOfBandSmsService.validateSmsOtpCode', 7);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.detect.radius.DetectIdRadiusAccessValidate.otbSmsValidate', 7);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.outofband.sms.ws.WSOutOfBandSmsService.retrieveNewOTP', 7);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.outofband.emailAndSms.ws.WSOutOfBandSmsAndMailService.createNewOTBSmsMail', 8);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.outofband.emailAndSms.ws.WSOutOfBandSmsAndMailService.retrieveNewOTP', 8);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.easysolTokens.ws.WSEasysolTokenService.validate', 9);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.easysolTokens.ws.WSEasysolTokenService.validateDID200', 10);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.easysolTokens.ws.WSEasysolTokenService.validateTokenDID300', 10);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.easysolTokens.ws.WSEasysolTokenService.validateToken', 10);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.easysolTokens.ws.impl.validation.HardTokenValidation.validateHardToken', 10);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.cq.ws.WSDetectCQService.isCQAnswerCorrect', 11);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.cq.ws.WSDetectCQService.isCQGroupAnswerCorrect', 11);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.thirdPartyTokens.ws.entities.WSThirdPartyTokensService.validateOTP', 9);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.detectid.ws.WSMachineFingerprintService.validate', 13);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.detect.radius.DetectIdRadiusAccessValidate.detectIDValidate', 13);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.gridcards.services.ws.WSGridCardService.evaluate', 14);
insert into operation_factor(operation_name, factor_id) values ('net.easysol.thirdPartyTokens.ws.impl.validation.ThirdPartyTokenValidation.validateThirdPartyOtp', 12);
--END OF SERVICES AND FACTORS

--AUTOMATIC CLIENT LOCK
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('client.automatic.lock.enable', 'false');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('client.automatic.unlock.enable', 'false');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('client.automatic.lock.max.failed.attempts', '5');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('client.automatic.lock.period.time', '86400000');
INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('client.automatic.lock.period.time.type', 'day');

CREATE SEQUENCE client_lock_history_id_seq;

CREATE TABLE client_lock_history(
  clh_id                  INT8  DEFAULT NEXTVAL('client_lock_history_id_seq'),
  clh_lock_date           BIGINT  NOT NULL,
  clh_lock_description    CHARACTER VARYING (300),
  clh_unlock_date         BIGINT,
  clh_unlock_description  CHARACTER VARYING (300),
  clh_lock_type           INT,
  clh_unlock_type         INT,
  cli_id_client           BIGSERIAL,
  CONSTRAINT client_lock_history_pkey PRIMARY KEY (clh_id)
);

--FK to Client
ALTER TABLE client_lock_history
ADD CONSTRAINT client_lock_history_client_fk FOREIGN KEY (cli_id_client)
REFERENCES client (cli_id_client) MATCH SIMPLE
ON UPDATE NO ACTION ON DELETE NO ACTION;

--Index to client
CREATE INDEX client_lock_history_idx_01 ON client_lock_history (cli_id_client);

INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('clientLockHistory.clean.period','0 0 0 0 3 ?');

--END OF AUTOMATIC CLIENT LOCK
-- EMAIL SERVER CONFIGURATIONS    INSTALL

CREATE TABLE email_server_configuration
(
  esc_id SERIAL NOT NULL,
  esc_name        VARCHAR(256) NOT NULL DEFAULT '',
  esc_description VARCHAR(256) DEFAULT '',
  esc_host        VARCHAR(256) DEFAULT '',
  esc_port        INT DEFAULT 0,
  esc_user        VARCHAR(256) DEFAULT '',
  esc_pwd		  VARCHAR(512) DEFAULT '',
  esc_email_account        VARCHAR(256) DEFAULT '',
  esc_protocol		  VARCHAR(64) DEFAULT '',

  CONSTRAINT email_server_configuration_pk PRIMARY KEY (esc_id)
);

CREATE UNIQUE INDEX unique_esc_name ON email_server_configuration USING BTREE (UPPER(esc_name));

-- END EMAIL SERVER CONFIGURATIONS

-- CHANNEL table
CREATE SEQUENCE channel_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 2  CACHE 1;

CREATE TABLE channel(
  ch_id      integer not null DEFAULT nextval('channel_id_seq' :: REGCLASS),
  ch_business_id    varchar(100),
  ch_description varchar(1024),
  ch_creation_date timestamp,
  esc_id integer,
  CONSTRAINT channel_pk PRIMARY KEY(ch_id)
);

ALTER TABLE channel ADD CONSTRAINT channel_email_conf_fk FOREIGN KEY (esc_id)
REFERENCES email_server_configuration (esc_id) MATCH SIMPLE
ON UPDATE CASCADE ON DELETE CASCADE;

CREATE UNIQUE INDEX channel_business_id_unique_key ON channel(ch_business_id);

--Default channel
INSERT INTO channel (ch_id, ch_business_id, ch_description, ch_creation_date) VALUES (1, 'General', 'General config', now());

CREATE TABLE channel_factor(
  chf_channel_id integer not null REFERENCES channel(ch_id),
  chf_factor_id integer not null REFERENCES factor(factor_id),
  chf_status    integer DEFAULT 1010,
  CONSTRAINT channel_factor_pk PRIMARY KEY(chf_channel_id,chf_factor_id)
);

INSERT INTO channel_factor (chf_channel_id, chf_factor_id, chf_status) SELECT 1, factor_id, 1010 from factor;

-- END OF CHANNEL

-- MOBILE CONFIGURATION table and migration data from detect_common_configurations
CREATE TABLE  mobile_auth_configuration(
  mcfg_id integer not null ,
  mcfg_dflt_msg varchar(1024)  default '' ,
  mcfg_dflt_tittle varchar(256)  default '' ,
  mcfg_dflt_text_msg integer  default 0 ,
  mcfg_devices_for_client integer  default 2 ,
  mcfg_challnge_cod_max_attempts integer  default 5 ,
  mcfg_activtn_code_expire_time integer  default 5 ,
  mcfg_transaction_expire_time integer  default 1 ,
  mcfg_email_send_notification integer  default 0 ,
  mcfg_device_enabled_default integer  default 0 ,
  mcfg_actvtion_cod_snd_retries integer  default 1 ,
  mcfg_send_email integer  default 1 ,
  mcfg_send_sms integer  default 0 ,
  mcfg_class_sender_sms varchar(1024)  default '' ,
  mcfg_class_sender_tran_state varchar(1024)  default '' ,
  mcfg_send_tran_state integer  default 0 ,
  mcfg_send_trans_state_by_email integer  default 0 ,
  mcfg_trans_state_receiver_mail varchar(512)  default '' ,
  mcfg_ios_cert_passwd varchar(256)  default '' ,
  mcfg_ios_certificate text  default '' ,
  mcfg_google_project_id varchar(512)  default '000000000' ,
  mcfg_subject_message_alert integer  default 0 ,
  mcfg_subject_alert_message varchar(512)  default '' ,
  mcfg_message_alert_message varchar(1024)  default '' ,
  mcfg_max_alert_messages_period integer  default 3 ,
  mcfg_period_time_alert_message integer  default 1 ,
  mcfg_bb_application_id varchar(512)  default '' ,
  mcfg_bb_application_pwd varchar(256)  default '' ,
  mcfg_bb_service_url varchar(512)  default '' ,
  mcfg_win_phone_app_id varchar(1024)  default '' ,
  mcfg_win_phone_app_pwd varchar(256)  default '' ,
  mcfg_qr_code_img_width integer  default 250 ,
  mcfg_qr_code_img_height integer  default 250 ,
  mcfg_qr_code_type varchar(5)  default 'JPG' ,
  mcfg_sms_ta_message text  default '' ,
  mcfg_email_ta_message text  default '' ,
  mcfg_email_ta_subject varchar(512)  default '' ,
  mcfg_email_ta_type varchar(50)  default 'HTML' ,
  mcfg_device_ta_message text  default '' ,
  mcfg_device_ta_subject varchar(512)  default '' ,
  mcfg_device_ta_type varchar(50)  default 'HTML' ,
  mcfg_ta_active integer  default 0 ,
  mcfg_ta_active_url_android integer  default 1 ,
  mcfg_ta_active_url_apple integer  default 1 ,
  mcfg_ta_title varchar(256)  default 'Lorem ipsum dolor sit amet' ,
  mcfg_ta_body text  default '<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In a condimentum nibh. Etiam vel eros nibh. Suspendisse convallis ante quam, sit amet auctor dui blandit non. Mauris sodales libero sed dui volutpat euismod. Suspendisse luctus viverra turpis ac imperdiet. Ut quis consectetur elit. Phasellus metus lorem, consectetur et tortor vulputate, feugiat elementum orci. In et dolor pretium, euismod felis quis, ultrices est. Vestibulum ipsum nisl, suscipit ut erat et, pretium ornare ligula. Pellentesque dictum mi in massa tristique, sed sagittis lorem lacinia. Cras lobortis auctor vehicula. Maecenas commodo vestibulum velit sit amet pretium. </p><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. In a condimentum nibh. Etiam vel eros nibh. Suspendisse convallis ante quam, sit amet auctor dui blandit non. Mauris sodales libero sed dui volutpat euismod. Suspendisse luctus viverra turpis ac imperdiet. Ut quis consectetur elit. Phasellus metus lorem, consectetur et tortor vulputate, feugiat elementum orci. In et dolor pretium, euismod felis quis, ultrices est. Vestibulum ipsum nisl, suscipit ut erat et, pretium ornare ligula. Pellentesque dictum mi in massa tristique, sed sagittis lorem lacinia. Cras lobortis auctor vehicula. Maecenas commodo vestibulum velit sit amet pretium. </p>' ,
  mcfg_ta_url_android varchar(256)  default 'com.easysolutions.detectid_otp' ,
  mcfg_ta_url_apple varchar(256)  default 'id620926344' ,
  mcfg_ta_text_button_accept varchar(256)  default 'Accept' ,
  mcfg_ta_text_button_decline varchar(256)  default 'Decline' ,
  CONSTRAINT mobile_auth_configuration_pk PRIMARY KEY(mcfg_id)
);


ALTER TABLE mobile_auth_configuration ADD CONSTRAINT mobileAuthConfiguration_Chfk FOREIGN KEY (mcfg_id)
REFERENCES channel (ch_id) MATCH SIMPLE
ON UPDATE CASCADE ON DELETE CASCADE;

--insert default configuration
INSERT INTO mobile_auth_configuration (mcfg_id) VALUES (1);

-- MOBILE_AUTH_TRANSACTIONS AND MOBILE ACTIVATION CODE with channel
ALTER TABLE mobile_auth_transactions ADD CONSTRAINT mobile_transactions_channel_fk FOREIGN KEY (ch_id)
REFERENCES channel (ch_id) MATCH SIMPLE
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE mobile_auth_gen_actv_cod ADD CONSTRAINT mobile_auth_gen_cod_fk FOREIGN KEY (ch_id)
REFERENCES channel (ch_id) MATCH SIMPLE
ON UPDATE CASCADE ON DELETE CASCADE;
--END OF MOBILE_AUTH_TRANSACTIONS AND MOBILE ACTIVATION CODE with channel

-- mobile_auth_alert_messages with channel
ALTER TABLE mobile_auth_alert_messages ADD CONSTRAINT mobile_auth_alert_msgs_channel FOREIGN KEY (ch_id)
REFERENCES channel (ch_id) MATCH SIMPLE
ON UPDATE CASCADE ON DELETE CASCADE;
-- end mobile_auth_alert_messages with channel

--  OUT OF BAND MAIL CONFIGURATION BEGIN
CREATE TABLE  oob_mail_configuration(
  oomcfg_id integer not null ,
  oomcfg_max_num_retries integer  default 5,
  oomcfg_otp_length integer default 6 ,
  oomcfg_otp_generation_type varchar(50) default 'NUMERIC'  ,
  oomcfg_otp_case_type varchar(50)  ,
  oomcfg_mail_subject varchar(512)  default '' ,
  oomcfg_otp_clean_time integer default 60000 ,
  oomcfg_mail_smtp_template text  default '' ,
  oomcfg_mail_smtp_type varchar(50)  default 'HTML' ,
  CONSTRAINT oob_mail_configuration_pk PRIMARY KEY(oomcfg_id)
);
ALTER TABLE oob_mail_configuration ADD CONSTRAINT oob_mail_config_channel FOREIGN KEY (oomcfg_id)
REFERENCES channel (ch_id) MATCH SIMPLE
ON UPDATE CASCADE ON DELETE CASCADE;

INSERT INTO oob_mail_configuration (oomcfg_id) VALUES (1);

--Out of band otp with channel
ALTER TABLE otbmail_otp ADD CONSTRAINT otb_otp_channel_fk FOREIGN KEY (ch_id)
REFERENCES channel (ch_id) MATCH SIMPLE
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE otbsms_otp ADD CONSTRAINT otb_otp_sms_channel_fk FOREIGN KEY (ch_id)
REFERENCES channel (ch_id) MATCH SIMPLE
ON UPDATE CASCADE ON DELETE CASCADE;

--  OUT OF BAND MAIL CONFIGURATION END

--  OUT OF BAND SMS CONFIGURATION BEGIN
CREATE TABLE oob_sms_configuration(
  ooscfg_id integer not null,
  ooscfg_max_num_retries integer default 5,
  ooscfg_template text default '',
  ooscfg_otp_length integer default 6,
  ooscfg_otp_generation_type varchar(50) default 'NUMERIC',
  ooscfg_sms_sender_class text default '',
  ooscfg_otp_case_type varchar(50),
  ooscfg_otp_clean_time integer default 60000,
  CONSTRAINT oob_sms_configuration_pk PRIMARY KEY(ooscfg_id)
);

ALTER TABLE oob_sms_configuration ADD CONSTRAINT oob_sms_config_channel FOREIGN KEY (ooscfg_id)
REFERENCES channel (ch_id) MATCH SIMPLE
ON UPDATE CASCADE ON DELETE CASCADE;

INSERT INTO oob_sms_configuration(ooscfg_id)VALUES(1);
--  OUT OF BAND SMS CONFIGURATION END

--  REPORTS
CREATE SEQUENCE report_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1  CACHE 1;

CREATE TABLE report(
  rep_id  INTEGER  NOT NULL DEFAULT nextval('report_id_seq' :: REGCLASS) ,
  adu_id_admin_user bigserial NOT NULL ,
  rep_type  VARCHAR(50)  NOT NULL ,
  rep_description VARCHAR(500) NULL,
  rep_generation_date BIGINT,
  rep_status  INTEGER NOT NULL,
  rep_number_pages  INTEGER,
  rep_percentage INTEGER
);

ALTER TABLE report ADD CONSTRAINT pk_report PRIMARY KEY (rep_id);

CREATE SEQUENCE report_page_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1  CACHE 1;

CREATE TABLE report_page(
  rpp_id  INTEGER  NOT NULL DEFAULT nextval('report_page_id_seq' :: REGCLASS),
  rpp_page_number INTEGER NOT NULL,
  rpp_page  TEXT,
  rpp_type  INTEGER,
  rep_id  INTEGER NOT NULL
);

ALTER TABLE report_page ADD CONSTRAINT pk_report_page PRIMARY KEY (rpp_id);
--  END OF REPORTS

--  DELETED CLIENTS

CREATE SEQUENCE deleted_client_id_seq;

CREATE TABLE deleted_client (
  dlc_id_client    INTEGER NOT NULL  DEFAULT NEXTVAL('deleted_client_id_seq'),
  dlc_shared_key   VARCHAR(256) NOT NULL,
  dlc_description  VARCHAR(256) NOT NULL,
  dlc_date_added   BIGINT NOT NULL,
  dlc_date_deleted BIGINT NOT NULL
);

INSERT INTO detect_common_configurations (dcc_identifier, dcc_conf_content) VALUES ('client.max.batch.deletion', '1000');

--  END DELETED CLIENTS

