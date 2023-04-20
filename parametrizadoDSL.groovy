// 1. aqui le decimos que crearemos una tarea
job('DSL job - hijo 001') {
    // 2. descripcion de la tarea pondremos esto
    description('Job Generado desde (DSL job) ')
    // 3. Source manager
    scm {
        git('https://github.com/macloujulian/jenkins.job.parametrizado','main') { node ->
            node / gitConfigName('cesar_jenkins')
            node / gitConfigEmail('perucaos@gmail.com')
        }
    }
    // 4. creacion de parametros
    parameters {
        stringParam('nombre',defaultValue='Cesar', description='paramtero del nombre')
        choiceParam('planeta',['Venus','Jupiter','Marte','Tierra'])
        booleanParam('agente',false)
    }
    // 5. Crear los trigers
    triggers {
        // cron que se ejecuta cada 7 minutos
        cron('H/7 * * * *')
    }
    // 6. Definir Pasos (ejecucion)
    steps {
        shell("bash jobscript.sh")
    }
    
    // 7. publisher (despues de ejecucion) ✅
    publishers {
        mailer('perucaos@gmail.com', true, true)
        slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(false)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
        }
    }

}