package com.linh.pfa.account.service;

import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.linh.pfa.account.entity.BankTransaction;
import com.linh.pfa.account.entity.BankTransactionRepository;

@Service
public class BankTransactionService {
	@Autowired
	private BankTransactionRepository bankTransactionRepository; 
	
	enum Header {UNKNOWN, POSB, OCBC, UOB};
	
	String HEADER_POSB = "Transaction Date,Reference,Debit Amount,Credit Amount,Transaction Ref1,Transaction Ref2,Transaction Ref3";
	String HEADER_OCBC = "Transaction date,Value date,Description,Withdrawals (SGD),Deposits (SGD)";
	String HEADER_UOB = "Transaction Date,Transaction Description,Withdrawal,Deposit,Available Balance";

	DateTimeFormatter posbDateformatter = DateTimeFormatter.ofPattern("dd MMM yyyy"); 
	DateTimeFormatter ocbcDateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
	
	@Transactional
	public void save(MultipartFile file) throws Exception {
		String content = new String(file.getBytes());
		String lines[] = content.split("\\r?\\n");
		
		List<BankTransaction> txns = new ArrayList<BankTransaction>();
		
		Header header = Header.UNKNOWN;
		for (int i=0; i<lines.length; i++) {
			String line=lines[i];
			if (header == Header.UNKNOWN) {
				if (line.startsWith(HEADER_POSB)) {
					header = Header.POSB;
				} else if (line.startsWith(HEADER_OCBC)) {
					header = Header.OCBC;
				} else if (line.startsWith(HEADER_UOB)) {
					header = Header.UOB;
				}
				continue;
			}
			
			BankTransaction bankTransaction = null;
			String bank = null;
			if (header == Header.POSB) {
				bank = "POSB";
				bankTransaction = parsePosbData(line);
			} else if (header == Header.OCBC) {
				bank = "OCBC";
				bankTransaction = parseOcbcData(line);
				if (bankTransaction != null && i < lines.length - 1 && lines[i+1].startsWith(",,")) { // same txn, just append description
					bankTransaction.setDescription(bankTransaction.getDescription() + " " + lines[i+1].substring(2));
					i++;
				}
			} else if (header == Header.UOB) {
				bank = "UOB";
				if (line.indexOf('"')!=-1) {
					// multiple line, add line until ending line is found
					int j = i + 1;
					while (lines[j].indexOf('"')==-1) {
						line = line + " " + lines[j];
						j++;
					}
					line = line + " " + lines[j];
					i = j;
				}
				bankTransaction = parseUobData(line);
			}
			
			if (bankTransaction != null && (bankTransaction.getDebit() != null || bankTransaction.getCredit() != null)) {
				bankTransaction.setBank(bank);
				txns.add(bankTransaction);
			}
		}

		txns.forEach(txn -> {
			if (bankTransactionRepository.findByTransactionDateAndDescription(txn.getTransactionDate(), txn.getDescription()).orElse(null)==null) {
				bankTransactionRepository.save(txn);
			}
		});
	}
	
	private BankTransaction parsePosbData(String line) {
		String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
		if (fields.length > 4) {
			BankTransaction bankTransaction = new BankTransaction();

			bankTransaction.setTransactionDate(LocalDate.parse(fields[0], posbDateformatter));
			bankTransaction.setRefCode(fields[1]);
			bankTransaction.setDebit(parseNumber(fields[2]));
			bankTransaction.setCredit(parseNumber(fields[3]));
			
			String description = fields[4]
					+ (fields.length>5 && !StringUtils.isEmpty(fields[5]) ? " " + fields[5] : "")
					+ (fields.length>6 && StringUtils.isEmpty(fields[6]) ? " " + fields[6] : "");
			bankTransaction.setDescription(description);
			return bankTransaction;
		}
		
		return null;
	}
	
	private BankTransaction parseOcbcData(String line) {
		String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
		if (fields.length > 3) {
			BankTransaction bankTransaction = new BankTransaction();

			bankTransaction.setTransactionDate(LocalDate.parse(fields[0], ocbcDateformatter));
			bankTransaction.setDescription(fields[2]);
			bankTransaction.setDebit(parseNumber(fields[3]));
			if (fields.length > 4) bankTransaction.setCredit(parseNumber(fields[4]));
			return bankTransaction;
		}
		return null;
	}

	private BankTransaction parseUobData(String line) {
		String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
		if (fields.length > 3) {
			BankTransaction bankTransaction = new BankTransaction();

			bankTransaction.setTransactionDate(LocalDate.parse(fields[0], posbDateformatter));
			bankTransaction.setDescription(fields[1].replace("\"", ""));
			bankTransaction.setDebit(parseNumber(fields[2]));
			bankTransaction.setCredit(parseNumber(fields[3]));
			return bankTransaction;
		}
		return null;
	}

	private BigDecimal parseNumber(String text) {
		String s = text.trim();
		s = s.replace("\"", "");
		s = s.replace(",", "");
		try {
			if (!StringUtils.isEmpty(s)) {
				return new BigDecimal(Double.parseDouble(s));
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
}
