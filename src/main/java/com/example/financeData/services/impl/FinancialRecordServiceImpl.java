package com.example.financeData.services.impl;

import com.example.financeData.dto.CategoryResponse;
import com.example.financeData.dto.MonthlyTrendResponse;
import com.example.financeData.dto.SummaryResponse;
import com.example.financeData.entities.FinancialRecord;
import com.example.financeData.entities.RecordType;
import com.example.financeData.entities.User;
import com.example.financeData.exceptions.ResourceNotFoundException;
import com.example.financeData.exceptions.UnauthorizedException;
import com.example.financeData.repository.FinancialRecordRepository;
import com.example.financeData.repository.UserRepository;
import com.example.financeData.services.FinancialRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FinancialRecordServiceImpl implements FinancialRecordService {

    private final FinancialRecordRepository recordRepository;
    private final UserRepository userRepository;

    private void checkAdminAccess(String role){
        if( !role.equals("ADMIN")){
            throw new UnauthorizedException("Access denied: Admin only");
        }
    }

    private String getUserRole(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getRole().name();
    }

    @Override
    public FinancialRecord createRecord(FinancialRecord record, Long userId) {

        String role= getUserRole(userId);

        checkAdminAccess(role);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        record.setCreatedBy(user);

        return recordRepository.save(record);
    }

    @Override
    public List<FinancialRecord> getAllRecords(Long userId) {

        String role= getUserRole(userId);

        if(role.equals("VIEWER") || role.equals("ANALYST") || role.equals("ADMIN")){
            return recordRepository.findAll();
        }
        throw new UnauthorizedException("Access denied");
    }

    @Override
    public FinancialRecord updateRecord(Long id, FinancialRecord updatedRecord, Long userId) {

        String role = getUserRole(userId);

        checkAdminAccess(role);

        FinancialRecord existing = recordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        existing.setAmount(updatedRecord.getAmount());
        existing.setCategory(updatedRecord.getCategory());
        existing.setType(updatedRecord.getType());
        existing.setLocalDate(updatedRecord.getLocalDate());
        existing.setDescription(updatedRecord.getDescription());

        return recordRepository.save(existing);
    }

    @Override
    public void deleteRecord(Long id, Long userId) {

        String role = getUserRole(userId);

        checkAdminAccess(role);

        if(!recordRepository.existsById(id)){
            throw new ResourceNotFoundException("Record Not Found");
        }
        recordRepository.deleteById(id);
    }

    @Override
    public List<FinancialRecord> filterRecords(RecordType type, String category, LocalDate startDate, LocalDate endDate, Long userId) {

        String role= getUserRole(userId);

        if(role.equals("VIEWER") || role.equals("ANALYST") || role.equals("ADMIN")){

            if(type != null && category != null){
                return recordRepository.findByTypeAndCategory(type, category);
            }

            if(startDate != null && endDate != null){
                return recordRepository.findByLocalDateBetween(startDate, endDate);
            }

            if(type != null){
                return recordRepository.findByType(type);
            }

            if(category != null){
                return recordRepository.findByCategory(category);
            }

            return recordRepository.findAll();
        }
        throw new UnauthorizedException("Access denied");
    }

    @Override
    public SummaryResponse getSummary(Long userId){

        String role= getUserRole(userId);

        if(role.equals("VIEWER") || role.equals("ANALYST") || role.equals("ADMIN")){

            List<FinancialRecord> records = recordRepository.findAll();

            double totalIncome=0;
            double totalExpense=0;

            for(FinancialRecord record: records){
                if(record.getType()== RecordType.INCOME){
                    totalIncome += record.getAmount();
                }
                else{
                    totalExpense += record.getAmount();
                }

            }
                double netBalance= totalIncome + totalExpense;

                return new SummaryResponse(totalIncome, totalExpense, netBalance);
        }
        throw new UnauthorizedException("Access Denied");
    }

    @Override
    public List<CategoryResponse> getCategorySummary(Long userId){

        String role= getUserRole(userId);

        if(role.equals("VIEWER") || role.equals("ANALYST") || role.equals("ADMIN")){

            List<FinancialRecord> records = recordRepository.findAll();

            Map<String, Double> categoryMap= new HashMap<>();

            for(FinancialRecord record: records){
                categoryMap.put(
                        record.getCategory(),
                        categoryMap.getOrDefault(record.getCategory(), 0.0) + record.getAmount());
            }

            List<CategoryResponse> response= new ArrayList<>();

            for(Map.Entry<String, Double> entry: categoryMap.entrySet()){
                response.add(new CategoryResponse(entry.getKey(), entry.getValue()));
            }
            return response;
        }
        throw new UnauthorizedException("Access denied");
    }

    @Override
    public List<MonthlyTrendResponse> getMonthlyTrends(Long userId){

        String role= getUserRole(userId);

        if(role.equals("VIEWER") || role.equals("ANALYST") || role.equals("ADMIN")){

            List<FinancialRecord> records= recordRepository.findAll();

            Map<String, Double> monthlyMap= new HashMap<>();

            for(FinancialRecord record: records){

                String month= record.getLocalDate().getMonth().toString();

                monthlyMap.put(
                        month,
                        monthlyMap.getOrDefault(month, 0.0) + record.getAmount()
                );
            }

            List<MonthlyTrendResponse> response= new ArrayList<>();

            for(Map.Entry<String, Double> entry: monthlyMap.entrySet()){

                response.add(new MonthlyTrendResponse(entry.getKey(), entry.getValue()));
            }

            return response;
        }
        throw new UnauthorizedException("Access denied");
    }
}
