import { API_BASE_URL } from '../constant/Constant';

export async function getAdmissionReport(reportRequest) {
    console.log(reportRequest)

    try{
        const response = await fetch(API_BASE_URL +'/admission?' +
            `isFake=${encodeURIComponent(reportRequest.fake)}`+
            `&yearOfBirth=${encodeURIComponent(reportRequest.yearOfBirth)}`+
            `&sexAtBirth=${encodeURIComponent(reportRequest.sexAtBirth)}` +
            `&ethnicity=${encodeURIComponent(reportRequest.ethnicity)}`);
        return await response.json();
    }catch(error) {
        return [];
    }

}