package services;

import utility.LuhnValidator;
import java.sql.SQLException;

public class VoucherService {
    private static VoucherService instance;
    private int activeVoucherId = -1;
    private int activeVoucherMaxBalance = 0;

    private VoucherService() {}

    public static VoucherService getInstance() {
        if (instance == null) instance = new VoucherService();
        return instance;
    }

    public boolean isVoucherStaged() {
        return activeVoucherId != -1;
    }

    public String stageVoucher(String content) throws IllegalArgumentException, SQLException {
        if (!content.matches("\\d{7}") || !LuhnValidator.isValid(content)) {
            throw new IllegalArgumentException("Neplatný formát poukážky!");
        }
        int scannedId = Integer.parseInt(content);
        int balance = SQL_Connect.getInstance().getVoucherBalance(scannedId);

        this.activeVoucherId = scannedId;
        this.activeVoucherMaxBalance = balance;

        return "Poukážka prijatá. Zostatok: " + String.format("%.2f", balance / 100.0) + " EUR. Zadajte sumu na úhradu.";
    }

    public int chargeVoucher(int amountToPay) throws IllegalArgumentException, SQLException {
        if (activeVoucherId == -1) throw new IllegalStateException("Žiadna poukážka nie je pripravená!");
        if (amountToPay <= 0) throw new IllegalArgumentException("Suma musí byť väčšia ako 0!");
        if (amountToPay > activeVoucherMaxBalance) {
            throw new IllegalArgumentException("Suma prevyšuje zostatok poukážky (" + String.format("%.2f", activeVoucherMaxBalance / 100.0) + " EUR)!");
        }

        int remainingBalance = SQL_Connect.getInstance().deductVoucherBalance(activeVoucherId, amountToPay);
        reset();
        return remainingBalance;
    }

    public void reset() {
        activeVoucherId = -1;
        activeVoucherMaxBalance = 0;
    }
}