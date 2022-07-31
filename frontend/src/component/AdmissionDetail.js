import React  from 'react';
import {Table} from 'react-bootstrap';
import "./styles.css";
import "bootstrap/dist/css/bootstrap.min.css";

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
                    <th scope="col">End DateTime</th>
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