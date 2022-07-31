import React, { useState} from 'react';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Header} from './component/Header'
import {SearchPanel} from './component/SearchPanel';
import {getAdmissionReport} from "./service/ReportService";
import {PatientInfos} from "./component/PatientInfos";

function App() {
    const [patientInfos, setPatientInfos] = useState([])
    const [reportRequest, setReportRequest] = useState({yearOfBirth : "all", sexAtBirth:"all" , ethnicity:"all", fake : true})


    const fetchAdmissionReport = () => {
        console.log(reportRequest);
        getAdmissionReport(reportRequest)
            .then(patientInfos => {
                console.log("App.js", patientInfos)
                setPatientInfos(patientInfos)
            });
    }

    const onChangeForm = (e) => {
        if (e.target.name === 'yearOfBirth') {
            reportRequest.yearOfBirth = (e.target.value !== undefined) ? e.target.value : "ALL";
        }
        else if (e.target.name === 'sexAtBirth') {
            reportRequest.sexAtBirth = (e.target.value !== undefined) ? e.target.value : "ALL";
        }
        else if (e.target.name === 'ethnicity') {
            reportRequest.ethnicity = (e.target.value !== undefined) ? e.target.value : "ALL";
        }
        else if (e.target.name === 'fake') {
            reportRequest.fake = e.target.checked;
        }
        setReportRequest(reportRequest)
    }

    return (
        <div className="App">
            <Header></Header>
            <div className="content">
                <SearchPanel onChangeForm={onChangeForm} fetchAdmissionReport={fetchAdmissionReport}></SearchPanel>
                <br/>
                <div>
                    <PatientInfos patientInfos={patientInfos}></PatientInfos>
                </div>
            </div>
        </div>
    );
}

export default App;
