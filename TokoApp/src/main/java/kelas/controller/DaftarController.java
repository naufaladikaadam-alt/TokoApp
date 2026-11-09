package kelas.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import kelas.Navigator;
import kelas.data.DataProduk;
import kelas.model.Produk;

public class DaftarController {
    // Form tambah
    @FXML private TextField namaField;
    @FXML private ComboBox<String> kategoriBox;
    @FXML private TextField hargaField;
    @FXML private Label pesanLabel;

    // Pencarian + tabel
    @FXML private TextField cariField;
    @FXML private TableView<Produk> tabel;
    @FXML private TableColumn<Produk, String> kodeCol;
    @FXML private TableColumn<Produk, String> namaCol;
    @FXML private TableColumn<Produk, String> kategoriCol;
    @FXML private TableColumn<Produk, Long> hargaCol;

    private Navigator navigator;
    private DataProduk data;
    private final ObservableList<Produk> dataTampilan =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        kategoriBox.getItems().addAll(Produk.MAKANAN, Produk.MINUMAN);
        kategoriBox.setValue(Produk.MAKANAN);

        kodeCol.setCellValueFactory(new PropertyValueFactory<>("kode"));
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        kategoriCol.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        hargaCol.setCellValueFactory(new PropertyValueFactory<>("harga"));

        // Nilai tetap Long (sorting numerik); hanya tampilannya diformat rupiah.
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(
                Locale.forLanguageTag("id-ID"));
        hargaCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : rupiah.format(item));
            }
        });

        tabel.setItems(dataTampilan);
        tabel.setPlaceholder(new Label("Belum ada produk yang sesuai."));
    }

    public void setKonteks(Navigator navigator, DataProduk data) {
        this.navigator = navigator;
        this.data = data;
        cari();
    }

    private void tampilPesan(String teks, boolean sukses) {
        pesanLabel.getStyleClass().removeAll("error", "success");
        pesanLabel.getStyleClass().add(sukses ? "success" : "error");
        pesanLabel.setText(teks);
    }

    @FXML
    private void tambah() {
        try {
            long harga = Long.parseLong(hargaField.getText().trim());
            Produk p = data.tambah(namaField.getText(), kategoriBox.getValue(), harga);
            namaField.clear();
            hargaField.clear();
            kategoriBox.setValue(Produk.MAKANAN);
            cariField.clear();
            cari();
            tampilPesan("Produk " + p.getKode() + " berhasil ditambahkan.", true);
        } catch (NumberFormatException e) {
            tampilPesan("Harga harus berupa bilangan bulat (contoh: 15000).", false);
        } catch (IllegalArgumentException e) {
            tampilPesan(e.getMessage(), false);
        }
    }

    @FXML
    private void cari() {
        if (data == null) {
            return;
        }
        dataTampilan.clear();
        String kata = cariField.getText().trim().toLowerCase(Locale.ROOT);
        for (Produk produk : data.getDaftar()) {
            if (produk.getNama().toLowerCase(Locale.ROOT).contains(kata)) {
                dataTampilan.add(produk);
            }
        }
        tabel.sort();
    }

    @FXML
    private void resetPencarian() {
        cariField.clear();
        cari();
    }

    @FXML
    private void detail() {
        Produk produk = tabel.getSelectionModel().getSelectedItem();
        if (produk == null) {
            tampilPesan("Pilih satu produk pada tabel dahulu.", false);
            return;
        }
        try {
            navigator.bukaDetail(produk);
        } catch (IOException e) {
            tampilPesan("Halaman detail gagal dibuka.", false);
            e.printStackTrace();
        }
    }
}
