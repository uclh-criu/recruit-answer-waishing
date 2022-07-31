import React from 'react';
import {Button, Form} from 'react-bootstrap';
import "./styles.css";
import "bootstrap/dist/css/bootstrap.min.css";

export const SearchPanel = ({onChangeForm , fetchAdmissionReport}) => {

    return(
        <div className="container">
            <div className="row">
                    <Form id="search-form">
                        <div className="searchBox">
                            <div className="searchElement">
                                <label>Search</label>
                            </div>
                            <div className="searchElement">
                                <input type="text" onChange={(e) => onChangeForm(e)}  className="form-control" name="yearOfBirth" id="yearOfBirth" aria-describedby="emailHelp" placeholder="Year Of Birth" />

                            </div>
                            <div className="searchElement">
                                <Form.Select onChange={(e) => onChangeForm(e)} className="form-control" name="sexAtBirth" id="sexAtBirth" aria-label="Default select example">
                                    <option value="ALL">All</option>
                                    <option value="M">M</option>
                                    <option value="F">F</option>
                                </Form.Select>
                            </div>
                            <div className="searchElement">
                                <Form.Select onChange={(e) => onChangeForm(e)}  className="form-control"  name="ethnicity" id="ethnicity"  aria-label="Default select example">
                                    <option value="ALL">All</option>
                                    <option value="White British">White British</option>
                                    <option value="Asian Mixed">Asian Mixed</option>
                                    <option value="Caribbean">Caribbean</option>
                                    <option value="White British">White British</option>
                                    <option value="Other Mixed">Other Mixed</option>
                                    <option value="Black African">Black African</option>
                                    <option value="Chinese">Chinese</option>
                                </Form.Select>
                            </div>
                            <div className="searchElement">
                                <Form.Check
                                    type="switch"
                                    id="fake"
                                    name="fake"
                                    label="fake"
                                    defaultChecked={true}
                                    onChange={(e) => onChangeForm(e)}
                                />
                            </div>
                            <div className="searchElement">
                                <Button type="button" onClick= {() => fetchAdmissionReport()} className="btn btn-danger">search</Button>
                            </div>
                        </div>

                    </Form>
                </div>
        </div>
    )
}