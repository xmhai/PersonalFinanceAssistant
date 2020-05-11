import React from 'react';
import {
    Container,
    Header
  } from 'semantic-ui-react'
  
class AppBreadcrumb extends React.Component {
    render() {
        return (
            <div id='breadcrumb'>
                <Container text style={{ marginTop: '5em' }}>
                    <Header as='h2'>Dashboard</Header>
                </Container>
            </div>
        );
    }
}

export default AppBreadcrumb;