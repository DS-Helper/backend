package com.project.ds_helper.domain.reservation.enums;

public enum ReservationStatus {
     SCHEDULED("예정"),
    COMPLETED("완료"),
    CANCELED("취소");

    private final String korean;

     ReservationStatus(String koraen){
         this.korean = koraen;
     }

     public String getKorean(){
         return korean;
     }
    
     // 한국어로 일치하는 status 찾기
     public ReservationStatus findStatusByString(String korean){
        for(ReservationStatus status : values()){
            if(status.getKorean().equals(korean)){
                return status;
            }
        }
        throw new IllegalArgumentException("No ReservationStatus Matched" + korean);
     }
}
