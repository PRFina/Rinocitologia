(defrule start
		(declare (salience 10000))
=>
		(controllo-calcolabilita)
)

(defrule condizione-normale-riniti-medicamentosa-gravidica
		(declare (salience 100))
        (cellula (nome Eosinofili) (grado 0))
        (cellula (nome Mastociti) (grado 0))
        (or (cellula (nome Neutrofili) (grado 0)) (cellula (nome Neutrofili) (grado 1)))
=>
        (assert (diagnosi(nome "condizione normale") (informazioni "Grado di  eosinofili: 0" "Grado di  mastociti: 0" "Grado di  neutrofili: 0-1")))
        (assert (diagnosi(nome "rinit medicamentosa") (informazioni "Grado di  eosinofili" "Grado di  mastociti")))
        (assert (diagnosi(nome "rinite gravidica") (informazioni "Grado di  eosinofili" "Grado di  mastociti")))
        (printout t "Probabile diagnosi: Condizione Normale, Rinite Medicamentosa, Rinite Gravidica." crlf)
        (printout t "Ricorrere ad anamnesi per maggiore precisione nella diagnosi." crlf)
)

(defrule rinite-allergica
		(declare (salience 100))
        (cellula (nome Eosinofili) (grado ?gradoE&:(and(> ?gradoE 0) (< ?gradoE 5))))
        (cellula (nome Mastociti) (grado ?gradoM&:(and(> ?gradoM 0) (< ?gradoM 5))))
        (cellula (nome Neutrofili) (grado ?gradoN&:(and(> ?gradoN 0) (< ?gradoN 5))))
=>
        (assert (diagnosi(nome "rinite allergica") (informazioni (str-cat "Grado di eosinofili: " ?gradoE) (str-cat "Grado di mastociti: " ?gradoM) (str-cat "Grado di neutrofili: " ?gradoN))))
)

(defrule NARES
		(declare (salience 100))
        (cellula (nome Eosinofili) (grado ?gradoE&:(and(> ?gradoE 0) (< ?gradoE 5))))
        (cellula (nome Mastociti) (grado 0))
        (cellula (nome Neutrofili) (grado ?gradoN&:(and(>= ?gradoN 0) (< ?gradoN 5))))
		(prick-test(esito negativo))
		(or (sintomo(nome ?ostruzione&:(eq (sub-string 1 10 ?ostruzione) "Ostruzione"))) 
			(sintomo(nome "Prurito nasale")) 
			(sintomo(nome "Bruciore congiuntivale")) 
			(sintomo(nome ?starnutazione&:(eq (sub-string 1 13 ?starnutazione) "Starnutazione"))) 
			(sintomo(nome ?olfatto&:(eq (sub-string 1 8 ?olfatto) "Problemi"))))
=>
        (assert (diagnosi(nome "NARES") (informazioni (str-cat "Grado di eosinofili: " ?gradoE) "Grado di  mastociti: 0" (str-cat "Grado di neutrofili: " ?gradoN) (aggiungi-informazioni (create$ "Prurito nasale" "Bruciore congiuntivale" "Ostruzione" "Starnutazione" "Problemi")))))
)

(defrule NARESMA
		(declare (salience 100))
        (cellula (nome Eosinofili) (grado ?gradoE&:(and(> ?gradoE 0) (< ?gradoE 5))))
        (cellula (nome Mastociti) (grado ?gradoM&:(and(> ?gradoM 0) (< ?gradoM 5))))
        (cellula (nome Neutrofili) (grado ?gradoN&:(and(>= ?gradoN 0) (< ?gradoN 5))))
=>
        (assert (diagnosi(nome "NARESMA") (informazioni (str-cat "Grado di eosinofili: " ?gradoE) (str-cat "Grado di mastociti: " ?gradoM) (str-cat "Grado di neutrofili: " ?gradoN))))
)

(defrule rinite_mastocitaria
		(declare (salience 100))
        (cellula (nome Eosinofili) (grado 0))
        (cellula (nome Mastociti) (grado ?gradoM&:(and(> ?gradoM 0) (< ?gradoM 5))))
=>
        (assert (diagnosi(nome "rinite mastocitaria") (informazioni "Grado di  eosinofili: 0" (str-cat "Grado di mastociti: " ?gradoM))))
)

(defrule riniti-irritativa-virale-atrofica
		(declare (salience 100))
        (cellula (nome Eosinofili) (grado 0))
        (cellula (nome Mastociti) (grado 0))
        (cellula (nome Neutrofili) (grado ?gradoN&:(and(> ?gradoN 0) (< ?gradoN 5))))
=>
        (assert (diagnosi(nome "rinite irritativa") (informazioni "Grado di  eosinofili: 0" "Grado di  mastociti: 0" (str-cat "Grado di neutrofili: " ?gradoN))))
        (assert (diagnosi(nome "rinite virale") (informazioni "Grado di  eosinofili: 0" "Grado di  mastociti: 0" (str-cat "Grado di neutrofili: " ?gradoN))))
        (assert (diagnosi(nome "rinite atrofica") (informazioni "Grado di  eosinofili: 0" "Grado di  mastociti: 0" (str-cat "Grado di neutrofili: " ?gradoN))))
        (printout t "Probabile diagnosi: Rinite irritativa, Rinite Virale, Rinite Atrofica." crlf)
        (printout t "La scelta della diagnosi più corretta tra le tre patologie riscontrare potrebbe richiedere il controllo delle cellula ciliate. Ad ogni modo è opportuno ricorrere ad anamnesi per maggiore precisione nella diagnosi." crlf)
)

(defrule rinosinusite
		(declare (salience 100))
        (cellula (nome Eosinofili) (grado 0))
        (cellula (nome Mastociti) (grado 0))
        (cellula (nome Neutrofili) (grado ?gradoN&:(and(> ?gradoN 0) (< ?gradoN 5))))
=>
        (assert (diagnosi(nome "rinosinusite") (informazioni "Grado di  eosinofili: 0" "Grado di  mastociti: 0" (str-cat "Grado di neutrofili: " ?gradoN))))
)

(defrule rinite_micotica
		(declare (salience 100))
        (cellula (nome Eosinofili) (grado 0))
        (cellula (nome Mastociti) (grado 0))
        (cellula (nome Neutrofili) (grado ?gradoN&:(and(> ?gradoN 0) (< ?gradoN 5))))
=>
        (assert (diagnosi(nome "rinite micotica") (informazioni "Grado di  eosinofili: 0" "Grado di  mastociti: 0" (str-cat "Grado di neutrofili: " ?gradoN))))
)

(defrule poliposi_nasale
		(declare (salience 100))
        (cellula (nome Eosinofili) (grado ?gradoE&:(and(> ?gradoE 0) (< ?gradoE 5))))
        (cellula (nome Mastociti) (grado ?gradoM&:(and(>= ?gradoM 0) (< ?gradoM 5))))
        (cellula (nome Neutrofili) (grado ?gradoN&:(and(>= ?gradoN 0) (< ?gradoN 5))))
		(famiglia (soggetto ?s)(disturbo poliposi))
=>
        (assert (diagnosi(nome "poliposi nasale") (informazioni (str-cat "Grado di eosinofili: " ?gradoE) (str-cat "Grado di mastociti: " ?gradoM) (str-cat "Grado di neutrofili: " ?gradoN) (str-cat "Un " ?s " ha presentato la poliposi"))))
)

(defrule polipo_antrocoanale
		(declare (salience 100))
        (cellula (nome Eosinofili) (grado 0))
        (cellula (nome Mastociti) (grado 0))
        (cellula (nome Neutrofili) (grado ?gradoN&:(and(> ?gradoN 0) (< ?gradoN 5))))
=>
        (assert (diagnosi(nome "poliposi nasale") (informazioni "Grado di eosinofili: 0" "Grado di mastociti: 0" (str-cat "Grado di neutrofili: " ?gradoN))))
)

(defrule citologia-anamnesi-assenti-pricktestpositivo
		(prick-test (esito positivo))
		(test (= (get-number-of-facts-by-name sintomo) 0))
		(test (= (get-number-of-facts-by-name famiglia) 0))
		(test (= (get-number-of-facts-by-name scoperta) 0))
		(test (= (get-number-of-facts-by-name rinomanometria) 0))
		(test (= (get-number-of-facts-by-name cellula) 0))
=>
		(assert (diagnosi (nome "rinite allergica") (informazioni "Sintomatologia assente" "Anamnesi familiare assente" "Esame del medico assente" "Citologia assente" "Prick-test: positivo")))
)

(defrule citologia-anamnesi-assenti-pricktestnegativo
		(prick-test (esito negativo))
		(test (= (get-number-of-facts-by-name sintomo) 0))
		(test (= (get-number-of-facts-by-name famiglia) 0))
		(test (= (get-number-of-facts-by-name scoperta) 0))
		(test (= (get-number-of-facts-by-name rinomanometria) 0))
		(test (= (get-number-of-facts-by-name cellula) 0))
=>
		(assert (diagnosi (nome "non calcolabile") (informazioni "Sintomatologia assente" "Anamnesi familiare assente" "Esame del medico assente" "Citologia assente" "Prick-test: negativo")))
)

(defrule anamnesi-assente-pricktestpositivo
		(or(prick-test (esito positivo) (periodo pollinico) (allergene stagionale)) (prick-test (esito positivo) (allergene perenne)))
		(test (= (get-number-of-facts-by-name sintomo) 0))
		(test (= (get-number-of-facts-by-name famiglia) 0))
		(test (= (get-number-of-facts-by-name scoperta) 0))
		(test (= (get-number-of-facts-by-name rinomanometria) 0))
		(test (= 0 (length$ (find-fact((?d diagnosi)) (eq ?d:nome "condizione normale")))))
=>
		(assert (diagnosi (nome "rinite allergica") (informazioni "Sintomatologia assente" "Anamnesi familiare assente" "Esame del medico assente" "Citologia: negativa" "Prick-test: positivo")))
)

(defrule anamnesi-assente-pricktestpositivo-apollinico
		(prick-test (esito positivo) (periodo apollinico) (allergene stagionale))
		(test (= (get-number-of-facts-by-name sintomo) 0))
		(test (= (get-number-of-facts-by-name famiglia) 0))
		(test (= (get-number-of-facts-by-name scoperta) 0))
		(test (= (get-number-of-facts-by-name rinomanometria) 0))
=>
		(assert (diagnosi (nome "rinite allergica") (informazioni "Sintomatologia assente" "Anamnesi familiare assente" "Esame del medico assente" "Prick-test: positivo con periodo apollinico e allergene stagionale")))
)

(defrule anamnesi-assente-pricktestpositivo-apollinico
		(prick-test (esito positivo) (periodo apollinico) (allergene stagionale))
		(diagnosi (nome "condizione normale"))
		(test (= (get-number-of-facts-by-name sintomo) 0))
		(test (= (get-number-of-facts-by-name famiglia) 0))
		(test (= (get-number-of-facts-by-name scoperta) 0))
		(test (= (get-number-of-facts-by-name rinomanometria) 0))
=>
		(assert (diagnosi (nome "rinite vasomotoria con sensibilizzazione allergenica") (informazioni "Sintomatologia assente" "Anamnesi familiare assente" "Esame del medico assente" "Citologia: negativa" "Prick-test: positivo con periodo apollinico e allergene stagionale")))
)

(defrule citologia-assente
		(prick-test (esito positivo))
		(test(= (length (find-all-facts ((?fct cellula)) TRUE)) 0))
=>
		(assert (diagnosi (nome "rinite allergica") (informazioni "Citologia assente" "Prick-test: positivo")))
)

(defrule rv-allergenica
		(prick-test (esito positivo) (allergene perenne))
		(diagnosi (nome "condizione normale"));CITOLOGIA NEGATIVA: fare la citologia (ossia cacthare le regole con solo citologia) i risultati li metti in un multislot di diagnosi
=>
		(assert (diagnosi (nome "rinite vasomotoria con sensibilizzazione allergenica") (informazioni "Prick-test: positivo, con allergene perenne" "Citologia: negativa")))
)

(defrule rv-allergenica
		(prick-test (esito positivo) (periodo apollinico) (allergene stagionale))
=>
		(assert (diagnosi (nome "rinite vasomotoria con sensibilizzazione allergenica") (informazioni "Prick-test: positivo, con periodo apollinico e allergene stagionale")))
)
