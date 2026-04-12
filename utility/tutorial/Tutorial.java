package utility.tutorial;

import assets.ButtonSet;
import controllers.notifications.NotificationController;
import controllers.transaction.ContentController;
import services.SQL_Connect;
import services.Users.CashierSession;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tutorial {
    private final List<TutorialStep> tutorial;
    controllers.panels.ViewManager vm = controllers.panels.ViewManager.getInstance();
    public Tutorial(){
         tutorial = new java.util.ArrayList<>();

         //Pause
        createPause();
        //Cashier actions
        createCashierAction();
        //PAYMENT
        createPayment();
        //RETURN TRANSACTION
        createReturnTransaction();
        //COPY RECEIPT
        createCopyReceipt();
        //GENERATE VOUCHER
        createGenerateVoucher();
        //CUSTOMER CARD
        createCustomerCard();


        //LAST STEP
        tutorial.add(new TutorialStep("HOTOVO",
                "",
                () -> null,
                () -> {
                    vm.returnToDefault();
                    vm.showIdle();
                    CashierSession.logout();
                    ContentController.clearContent();
                    try {
                        SQL_Connect.getInstance().disconnectAllFavoriteArticles(0);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                },
                TutorialStep.DialogPosition.TOP_LEFT
        ));
    }
    public List<TutorialStep> getTutorialSteps(){
        return tutorial;
    }

    private void createPause(){
        tutorial.add(new TutorialStep("Prihlásenie pokladníka",
                "Na začiatku zmeny sa pokladník vždy prihlási do svojho účtu pomocou svojho osobného čísla.",
                () -> null,
                () -> vm.showIdle(),
                TutorialStep.DialogPosition.TOP_LEFT
        ));
        tutorial.add(new TutorialStep("Prihlásenie pokladníka",
                "Kliknutím na" + TutorialStep.mention(ButtonSet.ButtonLabel.PAUSE) + "sa otvorí dialóg prihlasovania a systém sa uzamkne.",
                () -> vm.getDuringIdle().getButton(ButtonSet.ButtonLabel.PAUSE.toString()),
                () -> vm.showIdle(),
                TutorialStep.DialogPosition.TOP_LEFT
        ));
        tutorial.add(new TutorialStep("Prihlásenie pokladníka",
                "Pomocou klávesnice pokladník zadá svoje osobné číslo. Napísané číslo uvidí hore na displeji. V prípade omylu, pomocou" +
                         TutorialStep.mention(ButtonSet.ButtonLabel.BACKSPACE) + "vymažete poslednú zadanú číslicu, alebo pomocou"+ TutorialStep.mention(ButtonSet.ButtonLabel.DELETE) +" celé napísané číslo.",
                () -> vm.showPause(),
                TutorialStep.DialogPosition.TOP_LEFT,() -> java.util.Arrays.asList(
                vm.getDuringPause().getInputDisplay(),
                vm.getDuringPause().getKeyboard()
        )));
        tutorial.add(new TutorialStep("Prihlásenie pokladníka",
                "Zadané osobné  číslo  odošleme na overenie pomocu" + TutorialStep.mention(ButtonSet.ButtonLabel.LOGIN) +".",
                () -> vm.getDuringPause().getButton(ButtonSet.ButtonLabel.LOGIN.toString()),
                () -> vm.showPause(),
                TutorialStep.DialogPosition.AUTO
        ));
        tutorial.add(new TutorialStep("Odblokovanie systému",
                "V prípade že sme stlačili "+ TutorialStep.mention(ButtonSet.ButtonLabel.PAUSE) +" za účelom zablokovania systému, zadáme bezpečnostný kód " +
                        "vytlačený na bločku a odošleme ho na overenie pomocou tlačidla"+ TutorialStep.mention(ButtonSet.ButtonLabel.ADD) +".",
                () -> vm.getDuringPause().getButton(ButtonSet.ButtonLabel.CONFIRM.toString()),
                () -> {
                    CashierSession.login(0);
                    vm.getStatusBar().setLocked(false);
                },
                TutorialStep.DialogPosition.AUTO
        ));
    }

    private void createCashierAction(){
        tutorial.add(new TutorialStep("Blokovanie tovaru",
                "Pre blokovanie tovaru musíme najprv otvoriť transakciu. " +
                        "Transakcia sa otvorí po " +
                        "naskenovaní tovaru, alebo po otvorení dialógu artiklov -" + TutorialStep.mention(ButtonSet.ButtonLabel.BEGIN) + ".",
                () -> vm.getDuringIdle().getButton(ButtonSet.ButtonLabel.BEGIN.toString()),
                vm::showIdle,
                TutorialStep.DialogPosition.TOP_LEFT
        ));
        tutorial.add(new TutorialStep("Blokovanie tovaru",
                "Pre zadanie tovaru bez BAR kódu, máme dialóg artiklov. Tu sa dá tovar filtrovať pre rýchlejšiu navigáciu vo veľkom katalógu artiklov.",
                () -> vm.getDuringArticles().getArticleFilterButtonCluster(),
                vm::showArticles,
                TutorialStep.DialogPosition.AUTO
        ));
        tutorial.add(new TutorialStep("Blokovanie tovaru",
                "Tovar, ktorý chceme pridať do " +
                        "transakcie jednoducho zaklikneme. Daný artikel sa zvýrazní aby sme mali na vedomí ktorý tovar je práve aktívny.",
                () -> vm.getDuringArticles().getDisplayScrollableArticles(),
                vm::showArticles,
                TutorialStep.DialogPosition.TOP_LEFT
        ));
        tutorial.add(new TutorialStep("Blokovanie tovaru",
                "Pomocou klávesnice vieme zadať množstvo tovaru. Na displeji sa nám zobrazí počet pre jednoducú " +
                        "kontrolu. Množstvo tovaru je automaticky 1, ak inak nezadáme.",
                () -> vm.getDuringArticles().getKeypad(),
                vm::showArticles,
                TutorialStep.DialogPosition.TOP_RIGHT
        ));
        tutorial.add(new TutorialStep("Blokovanie tovaru",
                "Po zadaní požadovaného množstva sa môžeme rozhodnúť či chceme tovar pridať alebo ubrať.",
                vm::showArticles,
                TutorialStep.DialogPosition.TOP_RIGHT,
                        () -> java.util.Arrays.asList(
                vm.getDuringArticles().getButton(ButtonSet.ButtonLabel.ADD.toString()),
                vm.getDuringArticles().getButton(ButtonSet.ButtonLabel.REMOVE.toString())
        )));
        tutorial.add(new TutorialStep("Blokovanie tovaru",
                "Ak si chceme skontrolovať prehľad transakcie, môžme tak urobiť na displeji v dialógu artiklov alebo sa prepnúť do prehladu a platby " +
                        "pomocou "+TutorialStep.mention(ButtonSet.ButtonLabel.EXIT)+".",
                vm::showArticles,
                TutorialStep.DialogPosition.TOP_RIGHT,
                () -> java.util.Arrays.asList(
                        vm.getDuringArticles().getButton(ButtonSet.ButtonLabel.EXIT.toString()),
                        vm.getDuringArticles().getDisplayScrollableItems()
        )));
        tutorial.add(new TutorialStep("Praktická skúška",
                "Teraz si to vyskúšame v praxi! Vpravo hore sa ti zjaví zoznam položiek. Nablokuj ich presne podľa zadania.",
                () -> null,
                vm::showArticles,
                TutorialStep.DialogPosition.CENTER
        ));
        tutorial.add(new TutorialStep((nextStepCallback) -> {
            vm.getDuringArticles().getButton(ButtonSet.ButtonLabel.EXIT.toString()).setEnabled(false);
            vm.getMainFrame().getGlassPane().setVisible(false);

            List<TrainingObjective> targets = new ArrayList<>();
            targets.add(new TrainingObjective("Jablko Gala", 4));
            targets.add(new TrainingObjective("Rožok biely 40g", 4));

            TrainingScenarioOverlay view = new TrainingScenarioOverlay(vm.getMainFrame(), targets);

            TrainingScenarioController controller = new TrainingScenarioController(view, targets, ()->{
                vm.getDuringArticles().getButton(ButtonSet.ButtonLabel.EXIT.toString()).setEnabled(true);
                nextStepCallback.run();
            });

            services.OpenTransaction.addObserver(controller);
            view.setVisible(true);
        }));
        tutorial.add(new TutorialStep("Blokovanie tovaru",
                "Tak ako v dialógu artiklov, v " +
                        "prehlade nájdeme displeje a klávesnicu.",
                vm::showRegister,
                TutorialStep.DialogPosition.TOP_RIGHT,
                () -> java.util.Arrays.asList(
                        vm.getDuringRegister().getMiddlePanel(),
                        vm.getDuringRegister().getLeftPanel()
        )));
        tutorial.add(new TutorialStep("Blokovanie tovaru",
                "Manipuĺáciu transakcie vykonávame týmito troma tlačidlami.\n" +
                TutorialStep.mention(ButtonSet.ButtonLabel.ARTICLES)+"- dostaneme sa do dialógu artiklov\n" +
                TutorialStep.mention(ButtonSet.ButtonLabel.ADD)+"- pridáme do transakcie posledú naskenovanú položku s množstvom aké zadáme\n" +
                TutorialStep.mention(ButtonSet.ButtonLabel.STORNO)+"- odstránime z transakcie poslednú položku s množstvom ktoré zadáme." ,
                vm::showRegister,
                TutorialStep.DialogPosition.TOP_LEFT,
                () -> java.util.Arrays.asList(
                        vm.getDuringRegister().getButton(ButtonSet.ButtonLabel.ARTICLES.toString()),
                        vm.getDuringRegister().getButton(ButtonSet.ButtonLabel.LAST_ARTICLE.toString()),
                        vm.getDuringRegister().getButton(ButtonSet.ButtonLabel.STORNO.toString())
        )));

    }

    private void createPayment(){
        tutorial.add(new TutorialStep("Platba",
                "Po doblokovaní, transakciu môžme zaplatit podla voľby zákazníka " +
                        "štvormi spôsobmi a ich " +
                        "kombináciami." ,
                vm::showRegister,
                TutorialStep.DialogPosition.TOP_LEFT,
                () -> java.util.Arrays.asList(
                        vm.getDuringRegister().getButton(ButtonSet.ButtonLabel.CASH.toString()),
                        vm.getDuringRegister().getButton(ButtonSet.ButtonLabel.CARD.toString()),
                        vm.getDuringRegister().getButton(ButtonSet.ButtonLabel.USE_VOUCHER.toString()),
                        vm.getDuringRegister().getButton(ButtonSet.ButtonLabel.FOOD_TICKETS.toString())
        )));
        tutorial.add(new TutorialStep("Platba - Hotovosť",
                "Pre rýchly vklad, máme na výber hodnotu bankoviek od 5 do 100 eur. Po vklade sa financie pridajú do transakcie. Po vklade sumy väčšej ako je cena celého nákupu sa \n" +
                        "transakcia uzavrie. V prípade že zákazník chce kombinovanú platbu môžme pridať konkrétne množstvo pomocou klávesnice.",
                () -> vm.getDuringRegister().getRightPanel(),
                () -> vm.getDuringRegister().switchState(ButtonSet.ButtonLabel.CASH.toString()),
                TutorialStep.DialogPosition.TOP_LEFT
        ));
        tutorial.add(new TutorialStep("Platba - Karta",
                "Pre uhradenie kartou je princíp veľmi jednoduchý, stačí zadať na klávesnici objem financií a pridať.",
                () -> vm.getDuringRegister().getRightPanel(),
                () -> vm.getDuringRegister().switchState(ButtonSet.ButtonLabel.CARD.toString()),
                TutorialStep.DialogPosition.TOP_LEFT
        ));
        tutorial.add(new TutorialStep("Platba - Stravenky",
                "",
                () -> null ,
                () -> vm.getDuringRegister().switchState(ButtonSet.ButtonLabel.CARD.toString()),
                TutorialStep.DialogPosition.TOP_LEFT
        ));
        tutorial.add(new TutorialStep("Platba - Poukážky",
                "Systém nám odpovie a pokračujeme zadaním hodnoty, ktorú chceme uhradiť pomocou poukážky. Následne systém oznámi, koľko financií ostáva na poukážke.",
                () -> vm.getStatusBar().getNotificationLabel(),
                () -> {
                    vm.getDuringRegister().switchState(ButtonSet.ButtonLabel.USE_VOUCHER.toString());
                    NotificationController.notifyObservers("Tu bude nejaký výpis",2000);
                },
                TutorialStep.DialogPosition.TOP_LEFT
        ));
        tutorial.add(new TutorialStep("Zákaznická karta",
                "V pripade manuálneho vkladania zákazníckej karty, zadajte ID na zadnej strane zákaznickej katičky a odošlite do systému pomocou "+ TutorialStep.mention(ButtonSet.ButtonLabel.VALIDATE_CARD) +".",
                () -> vm.getDuringRegister().getRightPanel(),
                () -> {
                    vm.returnToDefault();
                    vm.showRegister();
                },
                TutorialStep.DialogPosition.TOP_LEFT
        ));
        tutorial.add(new TutorialStep("Praktická skúška",
                "Teraz bude vašou úlohou uhradiť transakciu hotovosťou.",
                () -> vm.getStatusBar().getNotificationLabel(),
                () -> {
                    vm.returnToDefault();
                    vm.showRegister();
                },
                TutorialStep.DialogPosition.CENTER
        ));
        tutorial.add(new TutorialStep((nextStepCallback) -> {
            vm.getMainFrame().getGlassPane().setVisible(false);
            vm.getDuringRegister().getButton(ButtonSet.ButtonLabel.ARTICLES.toString()).setEnabled(false);
            vm.getDuringRegister().getButton(ButtonSet.ButtonLabel.STORNO.toString()).setEnabled(false);
            vm.getDuringRegister().getButton(ButtonSet.ButtonLabel.LAST_ARTICLE.toString()).setEnabled(false);
            new PaymentTaskController("Hotovosť", () -> {
                vm.getDuringRegister().getButton(ButtonSet.ButtonLabel.ARTICLES.toString()).setEnabled(true);
                vm.getDuringRegister().getButton(ButtonSet.ButtonLabel.STORNO.toString()).setEnabled(true);
                vm.getDuringRegister().getButton(ButtonSet.ButtonLabel.LAST_ARTICLE.toString()).setEnabled(true);
                vm.getMainFrame().getGlassPane().setVisible(true);
                nextStepCallback.run();
            });
        }));
    }

    private void createReturnTransaction() {
        tutorial.add(new TutorialStep("Vrátenie tovaru (Vratka)",
                "V prípade, že zákazník chce vrátiť zakúpený tovar, kliknite na domovskej obrazovke na tlačidlo " + TutorialStep.mention(ButtonSet.ButtonLabel.RETURN) + ".",
                () -> vm.getDuringIdle().getButton(ButtonSet.ButtonLabel.RETURN.toString()),
                vm::showIdle,
                TutorialStep.DialogPosition.TOP_LEFT
        ));
        tutorial.add(new TutorialStep("Vrátenie tovaru - Číslo bloku",
                "Následne je potrebné zadať číslo pokladničného bloku, z ktorého sa tovar vracia. Číslo zadajte pomocou klávesnice a potvrďte.",
                vm::showCodeEnter,
                TutorialStep.DialogPosition.TOP_LEFT,
                () -> java.util.Arrays.asList(
                    vm.getDuringReturn().getMiddlePanel(),
                    vm.getDuringReturn().getRightPanel()
                )
        ));
        tutorial.add(new TutorialStep("Vrátenie tovaru - Výber položiek",
                "Po zadaní čísla bloku sa načíta pôvodná transakcia. V zozname nablokovaných položiek kliknutím označíte tie, ktoré chce zákazník vrátiť.",
                () -> vm.getDuringReturn().getDisplayScrollableItems(),
                vm::showReturnTransaction,
                TutorialStep.DialogPosition.TOP_RIGHT
        ));
        tutorial.add(new TutorialStep("Vrátenie tovaru - Úprava množstva",
                "Množstvo vráteného tovaru môžeme podľa potreby upraviť pomocou tlačidiel " + TutorialStep.mention(ButtonSet.ButtonLabel.ADD) + " a " + TutorialStep.mention(ButtonSet.ButtonLabel.REMOVE) + ".",
                vm::showReturnTransaction,
                TutorialStep.DialogPosition.TOP_LEFT,
                () -> java.util.Arrays.asList(
                        vm.getDuringReturn().getButton(ButtonSet.ButtonLabel.ADD.toString()),
                        vm.getDuringReturn().getButton(ButtonSet.ButtonLabel.REMOVE.toString())
                )
        ));
        tutorial.add(new TutorialStep("Vrátenie tovaru - Spôsob vrátenia",
                "Po úprave položiek nasleduje vrátenie financií. Zákazníkovi môžeme peniaze vrátiť v hotovosti stlačením" + TutorialStep.mention(ButtonSet.ButtonLabel.CASH) + "alebo priamo na platobnú kartu stlačením" + TutorialStep.mention(ButtonSet.ButtonLabel.CARD) + ".",
                () -> {
                    vm.showReturnTransaction();
                    vm.getDuringReturn().returnToDefault();
                },
                TutorialStep.DialogPosition.TOP_LEFT,
                () -> java.util.Arrays.asList(
                        vm.getDuringReturn().getButton(ButtonSet.ButtonLabel.CASH.toString()),
                        vm.getDuringReturn().getButton(ButtonSet.ButtonLabel.CARD.toString())
                )
        ));
        tutorial.add(new TutorialStep("Vrátenie tovaru - Hotovosť",
                "Pri výbere hotovosti transakciu dokončíme stlačením tlačidla" + TutorialStep.mention(ButtonSet.ButtonLabel.CASH_BACK) + ". Týmto sa otvorí pokladničná zásuvka a systém vrátenie zaznamená.",
                () -> vm.getDuringReturn().getButton(ButtonSet.ButtonLabel.CASH_BACK.toString()),
                () -> {
                    vm.showReturnTransaction();
                    vm.getDuringReturn().switchState(
                            new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ButtonSet.ButtonLabel.CASH.toString())
                    );
                },
                TutorialStep.DialogPosition.TOP_LEFT
        ));
        tutorial.add(new TutorialStep((nextStepCallback) -> {
            vm.getDuringReturn().getButton(ButtonSet.ButtonLabel.CASH.toString()).setEnabled(false);
            vm.getDuringReturn().getButton(ButtonSet.ButtonLabel.CARD.toString()).setEnabled(false);
            vm.getDuringReturn().switchState(
                    new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ButtonSet.ButtonLabel.EXIT.toString())
            );
            vm.getMainFrame().getGlassPane().setVisible(false);

            try {
                controllers.transaction.OpenTransactionManager.getInstance().loadHistoricalTransaction(
                        0,
                        SQL_Connect.getInstance().getDateOfTransaction(0),
                        SQL_Connect.getInstance().getAllArticlesFromPastTransaction(0)
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            vm.showReturnTransaction();

            List<TrainingObjective> targets = new ArrayList<>();
            targets.add(new TrainingObjective("Jablko Gala", 2));
            targets.add(new TrainingObjective("Rožok biely 40g", 1));

            TrainingScenarioOverlay view = new TrainingScenarioOverlay(vm.getMainFrame(), targets);
            new ReturnScenarioController(view, targets, () -> {
                vm.getDuringReturn().getButton(ButtonSet.ButtonLabel.CASH.toString()).setEnabled(true);
                vm.getDuringReturn().getButton(ButtonSet.ButtonLabel.CARD.toString()).setEnabled(true);
                vm.getMainFrame().getGlassPane().setVisible(true);
                nextStepCallback.run();
            });

            view.setVisible(true);
        }));
        tutorial.add(new TutorialStep("Vyplatenie hotovosti",
                "Výborne! Tovar je pripravený na vrátenie. Teraz klikni na " + TutorialStep.mention(ButtonSet.ButtonLabel.CASH) + " a vráť zákazníkovi vzniknutý schodok.",
                () -> vm.getDuringReturn().getButton(ButtonSet.ButtonLabel.CASH.toString()),
                () -> {
                    vm.showReturnTransaction();
                    vm.getDuringReturn().returnToDefault();
                },
                TutorialStep.DialogPosition.TOP_LEFT
        ));
        tutorial.add(new TutorialStep((nextStepCallback) -> {
            vm.getDuringReturn().getButton(ButtonSet.ButtonLabel.ADD.toString()).setEnabled(false);
            vm.getDuringReturn().getButton(ButtonSet.ButtonLabel.REMOVE.toString()).setEnabled(false);
            vm.getMainFrame().getGlassPane().setVisible(false);

            new PaymentTaskController("CASH", () -> {
                vm.getDuringReturn().getButton(ButtonSet.ButtonLabel.ADD.toString()).setEnabled(true);
                vm.getDuringReturn().getButton(ButtonSet.ButtonLabel.REMOVE.toString()).setEnabled(true);
                vm.getMainFrame().getGlassPane().setVisible(true);
                nextStepCallback.run();
            });
        }));
    }

    private void createCopyReceipt(){
        tutorial.add(new TutorialStep("Kópia pokladničného bloku",
                "V prípade potreby kópie pokladničného bloku, zaklikneme "+ TutorialStep.mention(ButtonSet.ButtonLabel.COPY_RECEIPT) +".",
                () -> vm.getDuringIdle().getButton(ButtonSet.ButtonLabel.COPY_RECEIPT.toString()),
                vm::showIdle,
                TutorialStep.DialogPosition.TOP_LEFT
        ));
        tutorial.add(new TutorialStep("Kópia pokladničného bloku",
                "Jednoducho zadáme poradové číslo bloku a odošleme ho do systému. Kópia bločku sa nám vytlačí.",
                vm::showCodeEnter,
                TutorialStep.DialogPosition.TOP_LEFT,
                () -> java.util.Arrays.asList(
                        vm.getDuringCodeEnter().getRightPanel(),
                        vm.getDuringCodeEnter().getMiddlePanel()
                )
        ));
    }

    private void createGenerateVoucher(){
        tutorial.add(new TutorialStep("Vybaviť poukážku",
                "Zákazník ma možnost zakúpiť poukážku v rôznych nominálnych " +
                        "hodnotách. V prípade že si takúto poukážku chce zákazník zakúpiť, otvoríme dialóg generovania poukážok -"+ TutorialStep.mention(ButtonSet.ButtonLabel.GENERATE_VOUCHER) +".",
                () -> vm.getDuringIdle().getButton(ButtonSet.ButtonLabel.GENERATE_VOUCHER.toString()),
                vm::showIdle,
                TutorialStep.DialogPosition.TOP_LEFT
        ));
        tutorial.add(new TutorialStep("Vybaviť poukážku",
                "Naskenovaním, alebo manuálnym sposobom načítame ID na zadnej strane poukážky a odošleme ho do systému.",
                vm::showCodeEnter,
                TutorialStep.DialogPosition.TOP_LEFT,
                () -> java.util.Arrays.asList(
                        vm.getDuringCodeEnter().getRightPanel(),
                        vm.getDuringCodeEnter().getMiddlePanel()
                )
        ));
    }

    private void createCustomerCard(){
        tutorial.add(new TutorialStep("Zaregistrovanie zákazníka",
                "Zákazník, ktorý má záujem o zákaznícky účet, vyplní prihlášku a odovzdá vám údaje. Následne zaklinete"+ TutorialStep.mention(ButtonSet.ButtonLabel.CREATE_CARD) +".",
                () -> vm.getDuringIdle().getButton(ButtonSet.ButtonLabel.CREATE_CARD.toString()),
                vm::showIdle,
                TutorialStep.DialogPosition.TOP_LEFT
        ));
        tutorial.add(new TutorialStep("Zaregistrovanie zákazníka",
                "Odlepíte kartičku a podobne ako pri manuálnom pridávaní karty do transakcie, zadáte ID kartičky a odošlete.",
                vm::showCodeEnter,
                TutorialStep.DialogPosition.TOP_LEFT,
                () -> java.util.Arrays.asList(
                        vm.getDuringCodeEnter().getRightPanel(),
                        vm.getDuringCodeEnter().getMiddlePanel()
                )
        ));
    }
}
