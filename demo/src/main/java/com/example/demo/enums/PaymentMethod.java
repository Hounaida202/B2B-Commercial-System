package com.example.demo.enums;

/**
 * Types de paiement acceptés
 */
public enum PaymentType {
    ESPECES,    // Paiement en espèces (max 20,000 DH)
    CHEQUE,     // Paiement par chèque (peut être différé)
    VIREMENT    // Paiement par virement bancaire
}