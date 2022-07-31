import React , { useState } from 'react';
import {Button, Table} from 'react-bootstrap';
import "./styles.css";
import "bootstrap/dist/css/bootstrap.min.css";

export const PatientInfos = ({patientInfos}) => {
    console.log('PatientInfo length:::', Object.keys(patientInfos).length)
    const [show, setShow] = useState(false);
    const [selectedData , setSelectedData] = useState({});

    if (Object.keys(patientInfos).length  === 0) return null

    const hanldeClick = (selectedData) => {
        console.log(selectedData);
        setSelectedData(selectedData);
        setShow(!show);
    };

    const hideModal = () => {
        setShow(false);
    };


    const PatientInfoRow = (patientInfo,index) => {
        console.log("patientInfo.patientId", patientInfo.patientId);
        let rows = [];
        const patientRow = (
            <tr key = {index} className={index%2 === 0?'odd':'even'}>
                <td>{patientInfo.patientId}</td>
                <td>{patientInfo.yearOfBirth}</td>
                <td>{patientInfo.sexAtBirth}</td>
                <td>{patientInfo.ethnicity}</td>
                <td>
                    <Button type="button" onClick={() => hanldeClick(patientInfo.admissions)} className="btn btn-warning">Admission Detail</Button>
                </td>
            </tr>
        )
        rows.push(patientRow);

        return rows
    }

    const patientInfoTable =  Object.keys(patientInfos).map((key,index) => PatientInfoRow(patientInfos[key],index))

    return(

        <div >
            <div className="patient-table">
                <Table striped bordered hover>
                    <thead>
                        <tr>
                            <th>Patient Id</th>
                            <th>Year Of Birth</th>
                            <th>Sex At Birth</th>
                            <th>Ethnicity</th>
                            <th>Admissions</th>
                        </tr>
                    </thead>
                    <tbody>
                    {patientInfoTable}
                    </tbody>
                </Table>
            </div>
            <div className="admission-table"><AdmissionDetails admissionDetails={selectedData} handleClose={hideModal} /></div>
        </div>

    )
}

export const AdmissionDetails = ({admissionDetails}) => {
    console.log('AdmissionDetails length:::', Object.keys(admissionDetails).length)

    if (Object.keys(admissionDetails).length  === 0) return null

    const AdmissionDetailRow = (admissionDetail,index) => {
        console.log("admissionDetail", admissionDetail[index]);
        return(
            <tr>
                <td>{admissionDetail.startDateTime}</td>
                <td>{admissionDetail.endDateTime}</td>
                <td>{admissionDetail.source}</td>
                <td>{admissionDetail.outcome}</td>
            </tr>
        )
    }

    const admissionDetailTable =  Object.keys(admissionDetails).map((key,index) => AdmissionDetailRow(admissionDetails[key],index))

    return (
        <div className="App">
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th scope="col">Start DateTime</th>
                    <th scope="col">End Date Time</th>
                    <th scope="col">Source</th>
                    <th scope="col">Outcome</th>
                </tr>
                </thead>
                <tbody>
                    {admissionDetailTable}
                </tbody>
            </Table>
        </div>

    )
}