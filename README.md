#AUTENTICAZIONE E PROFILAZIONE CON SPRING SECURITY E JWT DI UN ECOSISTEMA DI MICROSERVIZI GESTITI DA API GATEWAY 

L'ecosistema di microservizi è costituito da:
  1) un config sservice, che centralizza la configurazione leggendo la configurazione da un cartella (o repository git), posta nella cartella config 
  2) la cartella config, contenente la conigurazione da condividere
  3) Zuul-Service, api gateway che gestisce l'autenticazione e la profilazione dell'utenza, oltre ad avere le più classiche funzionalità di routing
  4) token-service, un microservizio pensato per generare e gestire i token jwt
  5) api-services, rappresenta il microservizio che espone le risorse all'utente, e che è di fatto ciò che dobbiamo proteggere
  
  Attraverso la chiamata auth a zuul service,viene rilasciato un token di autorizzazione. Questo token viene iniettato nell'header della richiesta, 
  e dovrà essere inviato dall'utente ad ogni chiamata. Le password sono in formato BCrypt (e così devono essere salvate nel interno del database interno).
  In fase di chiamata del servizio, il token passerà attrverso dei filtri di validazione (formato, scadenza ecc).
  Una volta passati i filltri di validazioni, si avvierà la profilazione basata su spring security.
  Se il token non passa i filtri di validazione, viene lanciata un'eccezione di risposta (codice 403).

  
